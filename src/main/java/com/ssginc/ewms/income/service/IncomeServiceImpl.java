package com.ssginc.ewms.income.service;

import com.ssginc.ewms.income.mapper.IncomeMapper;
import com.ssginc.ewms.income.vo.IncomeFormVO;
import com.ssginc.ewms.income.vo.IncomeProductSectorWarehouseInventoryVO;
import com.ssginc.ewms.income.vo.IncomeRequestVO;
import com.ssginc.ewms.income.vo.IncomeShipperProductSuppierVO;
import com.ssginc.ewms.product.mapper.ProductMapper;
import com.ssginc.ewms.product.vo.ProductVO;
import com.ssginc.ewms.sector.mapper.SectorMapper;
import com.ssginc.ewms.sector.vo.SectorVO;
import com.ssginc.ewms.shipper.mapper.ShipperMapper;
import com.ssginc.ewms.shipper.vo.ShipperVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

    private final IncomeMapper incomeMapper;
    private final ProductMapper productMapper;
    private final ShipperMapper shipperMapper;
    private final SectorMapper sectorMapper;


    @Override
    public List<IncomeShipperProductSuppierVO> fourFilterSelect(IncomeShipperProductSuppierVO incomeShipperProductSuppierVO){
        return incomeMapper.fourFilterSelect(incomeShipperProductSuppierVO);
    }//kjo-01


    @Override
    public List<IncomeShipperProductSuppierVO> detailsSelect(int incomeId){
        return incomeMapper.detailsSelect(incomeId);
    }//kjo-02

    @Override
    public List<IncomeShipperProductSuppierVO> selectIncomePrices(List<Integer> incomeIds){
        return incomeMapper.selectIncomePrices(incomeIds);
    }//kjo-03

    @Override
    public int updateCancleStatus(List<Integer> incomeIds){
        return incomeMapper.updateCancleStatus(incomeIds);
    }//kjo-04

    @Override
    public List<IncomeShipperProductSuppierVO> getExpectedIncomeList() {
        List<IncomeShipperProductSuppierVO> list = incomeMapper.getExpectedIncomeList();
        for (int i = 0; i <list.toArray().length ; i++) {
            String statusText = convertIncomeStatus(list.get(i).getIncomeStatus());
            list.get(i).setStatusText(statusText);
        }
        return list;
    }

    @Override
    public List<IncomeShipperProductSuppierVO> getExpectedNormalIncomeList(){
        return incomeMapper.getExpectedNormalIncomeList();
    }//kjo-06

    @Override
    public boolean updateUrgentIncomeProducts() {
        // 업데이트된 레코드 수를 반환받아 0보다 크면 업데이트 성공
        int updatedRows = incomeMapper.updateUrgentIncomeProducts();
        return updatedRows > 0;
    }

    @Override
    public int updateActualQuantityAndStatus(IncomeShipperProductSuppierVO incomeShipperProductSuppierVO){
        return incomeMapper.updateActualQuantityAndStatus(incomeShipperProductSuppierVO);
    }//kjo-08

    @Override
    public List<IncomeShipperProductSuppierVO> getUnderReviewList(){
        return incomeMapper.getUnderReviewList();
    }//kjo-09

    @Override
    public int updateUnderReview(List<Integer> incomeIds){
        return incomeMapper.updateUnderReview(incomeIds);
    }//kjo-10

    @Override
    public List<IncomeShipperProductSuppierVO> actualFilter(int productId){
        return incomeMapper.actualFilter(productId);
    }//kjo-11

    @Override
    public List<IncomeShipperProductSuppierVO> underReviewFilter(int productId){
        return incomeMapper.underReviewFilter(productId);
    }//kjo-12

    @Override
    public List<IncomeProductSectorWarehouseInventoryVO> getStorageInProgressList(){
        return incomeMapper.getStorageInProgressList();
    } //kjo-13

    @Override
    public List<IncomeProductSectorWarehouseInventoryVO> getStorageStatusByProductIdFIlter(int productId){
        return incomeMapper.getStorageStatusByProductIdFIlter(productId);
    }//kjo-14

    @Override
    public List<IncomeProductSectorWarehouseInventoryVO> getWarehouseSectorList(){
        return incomeMapper.getWarehouseSectorList();
    }//kj0-15

    @Override
    public List<IncomeProductSectorWarehouseInventoryVO> getSectorAvailableCapacity(int sectorId){
        return incomeMapper.getSectorAvailableCapacity(sectorId);
    }//kjo-16

    @Override
    public List<IncomeProductSectorWarehouseInventoryVO> getInspectionCapacity(int productId){
        return incomeMapper.getInspectionCapacity(productId);
    }//kjo-17

    @Override
    public int updateIncomeStatusComplete(Integer incomeId){
        return incomeMapper.updateIncomeStatusComplete(incomeId);
    }//kjo-18


    @Override
    public List<Integer> selectWarehouseId() {
        return incomeMapper.selectWarehouseId();
    }

    @Override
    public IncomeFormVO getIncomeFormByProductId(int inventoryId) {
        IncomeFormVO incomeFormVO = incomeMapper.getIncomeFormByInventoryId(inventoryId);
        return incomeFormVO;
    }

    @Override
    public int insertIncomeRequest(IncomeFormVO incomeRequest) {
        IncomeRequestVO incomeRequestVO = new IncomeRequestVO();

        ProductVO productVO = productMapper.getProductByName(incomeRequest.getProductName());
        int productId = productMapper.getProductIdByName(incomeRequest.getProductName());
        ShipperVO shipperVO = shipperMapper.getShipperByName(incomeRequest.getShipperName());
        SectorVO sectorVO = sectorMapper.findSectorByName(incomeRequest.getSectorName());

        if (shipperVO == null || sectorVO == null) {
            throw new NullPointerException();
        }

        incomeRequestVO.setProductId(productId);
        incomeRequestVO.setShipperId(shipperVO.getShipperId());

        if (incomeRequest.getIncomeType().equals("normalIncome")) {
            incomeRequestVO.setIncomeType(0);
            incomeRequestVO.setIncomeStatus(0);

        } else if (incomeRequest.getIncomeType().equals("emergencyIncome")) {
            incomeRequestVO.setIncomeType(1);
            incomeRequestVO.setIncomeStatus(1);
        }
        incomeRequestVO.setIncomePrice(productVO.getIncomeUnitPrice() * incomeRequest.getIncomeQuantity());
        incomeRequestVO.setIncomeExpectedQuantity(incomeRequest.getIncomeQuantity());
        incomeRequestVO.setIncomeExpectedDate(LocalDate.parse(incomeRequest.getIncomeExpectedDate()));
        incomeRequestVO.setSectorId(sectorVO.getSectorId());

        return incomeMapper.insertIncomeRequest(incomeRequestVO);
    }
}
