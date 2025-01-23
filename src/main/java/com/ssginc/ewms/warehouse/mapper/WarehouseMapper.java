package com.ssginc.ewms.warehouse.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Map;

@Mapper
public interface WarehouseMapper {


    int getWarehouseIdByInventoryId(int inventoryId);

    Map<String, BigDecimal> selectWarehouseLocationById(int warehouseId);
}
