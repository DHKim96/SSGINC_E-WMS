package com.ssginc.ewms.income.service;

import com.ssginc.ewms.income.mapper.IncomeMapper;
import com.ssginc.ewms.income.vo.IncomeProductSectorWarehouseInventoryVO;
import com.ssginc.ewms.income.vo.IncomeShipperProductSuppierVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IncomeService {

    private String convertIncomeStatus(int status) {//입고 상태는 int값이여서 stringd으로 변환 해주는거임
        switch (status) {
            case 0: return "입고예정";
            case 1: return "검수중";
            case 2: return "적치중";
            case 3: return "입고완료";
            case 4: return "입고취소";
            default: return "알 수 없음";
        }
    }
    private final IncomeMapper incomeMapper;


    public List<IncomeShipperProductSuppierVO> fourFilterSelect(IncomeShipperProductSuppierVO incomeShipperProductSuppierVO){
        return incomeMapper.fourFilterSelect(incomeShipperProductSuppierVO);
    }//kjo-01


    public List<IncomeShipperProductSuppierVO> detailsSelect(int incomeId){
        return incomeMapper.detailsSelect(incomeId);
    }//kjo-02

    public List<IncomeShipperProductSuppierVO> selectIncomePrices(List<Integer> incomeIds){
        return incomeMapper.selectIncomePrices(incomeIds);
    }//kjo-03

    public int updateCancleStatus(List<Integer> incomeIds){
        return incomeMapper.updateCancleStatus(incomeIds);
    }//kjo-04

    public List<IncomeShipperProductSuppierVO> getExpectedIncomeList() {
        List<IncomeShipperProductSuppierVO> list = incomeMapper.getExpectedIncomeList();
        for (int i = 0; i <list.toArray().length ; i++) {
            String statusText = convertIncomeStatus(list.get(i).getIncomeStatus());
            list.get(i).setStatusText(statusText);
        }
        return list;
    }

    public List<IncomeShipperProductSuppierVO> getExpectedNormalIncomeList(){
        return incomeMapper.getExpectedNormalIncomeList();
    }//kjo-06

    public List<IncomeShipperProductSuppierVO> getUrgentIncomeProducts(){
        return incomeMapper.getUrgentIncomeProducts();
    } //kjo-07

    public int updateActualQuantityAndStatus(IncomeShipperProductSuppierVO incomeShipperProductSuppierVO){
        return incomeMapper.updateActualQuantityAndStatus(incomeShipperProductSuppierVO);
    }//kjo-08

    public List<IncomeShipperProductSuppierVO> getUnderReviewList(){
        return incomeMapper.getUnderReviewList();
    }//kjo-09

    public int updateUnderReview(List<Integer> incomeIds){
        return incomeMapper.updateUnderReview(incomeIds);
    }//kjo-10

    public List<IncomeShipperProductSuppierVO> actualFilter(int productId){
        return incomeMapper.actualFilter(productId);
    }//kjo-11

    public List<IncomeShipperProductSuppierVO> underReviewFilter(int productId){
        return incomeMapper.underReviewFilter(productId);
    }//kjo-12

    public List<IncomeProductSectorWarehouseInventoryVO> getStorageInProgressList(){
        return incomeMapper.getStorageInProgressList();
    } //kjo-13

    public List<IncomeProductSectorWarehouseInventoryVO> getStorageStatusByProductIdFIlter(int productId){
        return incomeMapper.getStorageStatusByProductIdFIlter(productId);
    }//kjo-14

    public List<IncomeProductSectorWarehouseInventoryVO> getWarehouseSectorList(){
        return incomeMapper.getWarehouseSectorList();
    }//kj0-15

    public List<IncomeProductSectorWarehouseInventoryVO> getSectorAvailableCapacity(int sectorId){
        return incomeMapper.getSectorAvailableCapacity(sectorId);
    }//kjo-16

    public List<IncomeProductSectorWarehouseInventoryVO> getInspectionCapacity(int productId){
        return incomeMapper.getInspectionCapacity(productId);
    }//kjo-17

    public int updateIncomeStatusComplete(Integer incomeId){
        return incomeMapper.updateIncomeStatusComplete(incomeId);
    }//kjo-18


    public List<Integer> selectWarehouseId() {
        return incomeMapper.selectWarehouseId();
    }
}