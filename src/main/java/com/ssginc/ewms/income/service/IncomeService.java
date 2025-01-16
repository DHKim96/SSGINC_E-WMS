package com.ssginc.ewms.income.service;

import com.ssginc.ewms.income.vo.IncomeFormVO;
import com.ssginc.ewms.income.vo.IncomeProductSectorWarehouseInventoryVO;
import com.ssginc.ewms.income.vo.IncomeShipperProductSuppierVO;

import java.util.List;

public interface IncomeService {
    default String convertIncomeStatus(int status) {//입고 상태는 int값이여서 stringd으로 변환 해주는거임
        switch (status) {
            case 0:
                return "입고예정";
            case 1:
                return "검수중";
            case 2:
                return "적치중";
            case 3:
                return "입고완료";
            case 4:
                return "입고취소";
            default:
                return "알 수 없음";
        }
    }

    List<IncomeShipperProductSuppierVO> fourFilterSelect(IncomeShipperProductSuppierVO incomeShipperProductSuppierVO)//kjo-01
    ;

    List<IncomeShipperProductSuppierVO> detailsSelect(int incomeId)//kjo-02
    ;

    List<IncomeShipperProductSuppierVO> selectIncomePrices(List<Integer> incomeIds)//kjo-03
    ;

    int updateCancleStatus(List<Integer> incomeIds)//kjo-04
    ;

    List<IncomeShipperProductSuppierVO> getExpectedIncomeList();

    List<IncomeShipperProductSuppierVO> getExpectedNormalIncomeList()//kjo-06
    ;

    boolean updateUrgentIncomeProducts();

    int updateActualQuantityAndStatus(IncomeShipperProductSuppierVO incomeShipperProductSuppierVO)//kjo-08
    ;

    List<IncomeShipperProductSuppierVO> getUnderReviewList()//kjo-09
    ;

    int updateUnderReview(List<Integer> incomeIds)//kjo-10
    ;

    List<IncomeShipperProductSuppierVO> actualFilter(int productId)//kjo-11
    ;

    List<IncomeShipperProductSuppierVO> underReviewFilter(int productId)//kjo-12
    ;

    List<IncomeProductSectorWarehouseInventoryVO> getStorageInProgressList() //kjo-13
    ;

    List<IncomeProductSectorWarehouseInventoryVO> getStorageStatusByProductIdFIlter(int productId)//kjo-14
    ;

    List<IncomeProductSectorWarehouseInventoryVO> getWarehouseSectorList()//kj0-15
    ;

    List<IncomeProductSectorWarehouseInventoryVO> getSectorAvailableCapacity(int sectorId)//kjo-16
    ;

    List<IncomeProductSectorWarehouseInventoryVO> getInspectionCapacity(int productId)//kjo-17
    ;

    int updateIncomeStatusComplete(Integer incomeId)//kjo-18
    ;

    List<Integer> selectWarehouseId();

    IncomeFormVO getIncomeFormByProductId(int inventoryId);

    int insertIncomeRequest(IncomeFormVO incomeRequest);
}
