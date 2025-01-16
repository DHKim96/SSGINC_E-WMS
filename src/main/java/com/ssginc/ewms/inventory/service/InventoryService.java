package com.ssginc.ewms.inventory.service;

import com.ssginc.ewms.inventory.vo.InventoryAdjustVO;
import com.ssginc.ewms.inventory.vo.InventoryStateVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InventoryService {
    List<InventoryStateVO> getProductInventory(int warehouseId);

    List<InventoryStateVO> searchInventory(String sectorName,
                                           String startDate,
                                           String endDate,
                                           String productName,
                                           String supplierName);

    List<InventoryAdjustVO> getProductAdjustInventory(int warehouseId);

    @Transactional
    int updateRealInventory(List<Integer> idList, List<Integer> realQuantityList);

    @Transactional
    int updateQuantity(List<Integer> idList, List<Integer> realQuantityList);

    @Transactional
    int updateSector(List<Integer> inventoryIdList, List<String> sectorNameList);

    @Transactional
    int deleteInventories(List<Integer> inventoryIdList);
}
