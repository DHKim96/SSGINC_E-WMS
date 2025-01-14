package com.ssginc.ewms.outgoing;

import com.ssginc.ewms.branch.mapper.BranchMapper;
import com.ssginc.ewms.branch.vo.BranchVO;
import com.ssginc.ewms.exception.ValueCustomException;
import com.ssginc.ewms.outgoing.mapper.OutgoingMapper;
import com.ssginc.ewms.outgoing.vo.OutgoingFormVO;
import com.ssginc.ewms.outgoing.vo.OutgoingRequestVO;
import com.ssginc.ewms.product.mapper.ProductMapper;
import com.ssginc.ewms.product.vo.ProductVO;
import com.ssginc.ewms.sector.mapper.SectorMapper;
import com.ssginc.ewms.sector.vo.SectorVO;
import com.ssginc.ewms.shipper.mapper.ShipperMapper;
import com.ssginc.ewms.shipper.vo.ShipperVO;
import com.ssginc.ewms.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OutgoingServiceTemp {
    private final OutgoingMapperTemp outgoingMapper;
    private final ProductMapper productMapper;
    private final ShipperMapper shipperMapper;
    private final BranchMapper branchMapper;
    private final SectorMapper sectorMapper;

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
