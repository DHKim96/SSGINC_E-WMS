package com.ssginc.ewms.outgoing.service;

import com.ssginc.ewms.branch.mapper.BranchMapper;
import com.ssginc.ewms.branch.vo.BranchVO;
import com.ssginc.ewms.exception.ValueCustomException;
import com.ssginc.ewms.outgoing.mapper.OutgoingMapper;
import com.ssginc.ewms.outgoing.vo.OutgoingFormVO;
import com.ssginc.ewms.outgoing.vo.OutgoingRequestVO;
import com.ssginc.ewms.outgoing.vo.OutgoingVO;
import com.ssginc.ewms.product.mapper.ProductMapper;
import com.ssginc.ewms.product.vo.ProductVO;
import com.ssginc.ewms.sector.mapper.SectorMapper;
import com.ssginc.ewms.sector.vo.SectorVO;
import com.ssginc.ewms.shipper.mapper.ShipperMapper;
import com.ssginc.ewms.shipper.vo.ShipperVO;
import com.ssginc.ewms.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OutgoingServiceImpl implements OutgoingService {
    private final ProductMapper productMapper;
    private final ShipperMapper shipperMapper;
    private final BranchMapper branchMapper;
    private final SectorMapper sectorMapper;

    private final OutgoingMapper outgoingMapper;


    public List<OutgoingVO> getOutgoingBySearch(String startDate, String endDate, String productName, String productStatus) {
        return outgoingMapper.getOutgoingList(startDate, endDate, productName, productStatus);
    }

    public void updateOutgoingStatus(int outgoingId, int status) {
        outgoingMapper.updateOutgoingStatus(outgoingId, status);
    }
    
    public List<OutgoingVO> getOutgoingWithInventory(String startDate, String endDate, String productName, String productStatus) {
        return outgoingMapper.getOutgoingWithInventory(startDate, endDate, productName, productStatus);
    }

    @Transactional
    public void updateOutgoingStatusAndQuantity(int outgoingId, int status) {
        // 재고 검증
        Integer inventoryQuantity = outgoingMapper.getInventoryQuantity(outgoingId);
        Integer outgoingQuantity = outgoingMapper.getOutgoingQuantity(outgoingId);

        if (inventoryQuantity == null || outgoingQuantity == null) {
            throw new IllegalStateException("유효하지 않은 ID입니다.");
        }

        // 재고 부족 시 예외 발생
        if (inventoryQuantity < outgoingQuantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }

        // 상태 업데이트
        outgoingMapper.updateOutgoingStatus(outgoingId, status);

        // 재고 업데이트
        outgoingMapper.updateQuantity(outgoingId);

        // 날짜 업데이트
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        outgoingMapper.updateOutgoingDate(outgoingId, currentDateTime); // 상태도 함께 업데이트
    }
    public OutgoingFormVO getOutgoingFormByInventoryId(int inventoryId) {
        return outgoingMapper.getOutgoingFormByProductId(inventoryId);
    }

    public int insertOutgoingRequest(OutgoingFormVO outgoingForm) {
        OutgoingRequestVO outgoingRequestVO = new OutgoingRequestVO();

        ProductVO productVO = productMapper.getProductByName(outgoingForm.getProductName());
        ShipperVO shipperVO = shipperMapper.getShipperByName(outgoingForm.getShipperName());
        BranchVO branchVO = branchMapper.getBranchByName(outgoingForm.getBranchName());
        SectorVO sectorVO = sectorMapper.findSectorByName(outgoingForm.getSectorName());

        System.out.println(productVO);
        System.out.println(shipperVO);
        System.out.println(branchVO);
        System.out.println(sectorVO);

        if (productVO == null || branchVO == null || sectorVO == null || shipperVO == null) {
            throw new ValueCustomException(ErrorCode.NULL_POINT_ERROR);
        }


        outgoingRequestVO.setProductId(productVO.getProductId());
        outgoingRequestVO.setShipperId(shipperVO.getShipperId());
        if (outgoingForm.getOutgoingType().equals("normalOutgoing")) {
            outgoingRequestVO.setOutgoingType(0);
            outgoingRequestVO.setOutgoingStatus(0);
        } else if (outgoingForm.getOutgoingType().equals("emergencyOutgoing")) {
            outgoingRequestVO.setOutgoingType(1);
            outgoingRequestVO.setOutgoingStatus(1);
        }
        outgoingRequestVO.setOutgoingQuantity(outgoingForm.getOutgoingQuantity());
        outgoingRequestVO.setOutgoingPrice(productVO.getOutgoingUnitPrice() * outgoingForm.getOutgoingQuantity());
        outgoingRequestVO.setBranchId(branchVO.getBranchId());
        outgoingRequestVO.setSectorId(sectorVO.getSectorId());

        System.out.println(outgoingRequestVO);
        return outgoingMapper.insertOutgoingRequest(outgoingRequestVO);
    }

    public OutgoingFormVO getOutgoingFormByProductId(int productId) {
        return outgoingMapper.getOutgoingFormByProductId(productId);
    }
}