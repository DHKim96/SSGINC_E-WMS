package com.ssginc.ewms.income.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.ssginc.ewms.income.vo.IncomeShipperProductSuppierVO;
import com.ssginc.ewms.income.vo.IncomeProductSectorWarehouseInventoryVO;

import java.util.List;
import java.util.Map;

@Mapper
public interface IncomeMapper {
    List<IncomeShipperProductSuppierVO> fourFilterSelect(IncomeShipperProductSuppierVO incomeShipperProductSuppierVO);//kjo-01


    List<IncomeShipperProductSuppierVO> detailsSelect(int incomeId);//kjo-02

    List<IncomeShipperProductSuppierVO> selectIncomePrices(List<Integer> incomeIds);//kjo-03
    int updateCancleStatus(List<Integer> incomeIds);//kjo-04
    List<IncomeShipperProductSuppierVO> getExpectedIncomeList();//kjo-05

    List<IncomeShipperProductSuppierVO> getExpectedNormalIncomeList();//kjo-06

    List<IncomeShipperProductSuppierVO> getUrgentIncomeProducts(); //kjo-07

    int updateActualQuantityAndStatus(IncomeShipperProductSuppierVO incomeShipperProductSuppierVO);//kjo-08

    List<IncomeShipperProductSuppierVO> getUnderReviewList();//kjo-09

    int updateUnderReview(List<Integer> incomeIds);//kjo-10

    List<IncomeShipperProductSuppierVO> actualFilter(int productId);//kjo-11
    List<IncomeShipperProductSuppierVO> underReviewFilter(int productId);//kjo-12

    List<IncomeProductSectorWarehouseInventoryVO> getStorageInProgressList(); //kjo-13

    List<IncomeProductSectorWarehouseInventoryVO> getStorageStatusByProductIdFIlter(int productId);//kjo-14

    List<IncomeProductSectorWarehouseInventoryVO> getWarehouseSectorList();//kj0-15

    List<IncomeProductSectorWarehouseInventoryVO> getSectorAvailableCapacity(int sectorId);//kjo-16

    List<IncomeProductSectorWarehouseInventoryVO> getInspectionCapacity(int productId);//kjo-17
    List<Integer> selectWarehouseId();//kj0-18


    int updateIncomeStatusComplete(Integer productId);//kjo-19


}










