package com.ssginc.ewms.warehouse.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WarehouseMapper {


    int getWarehouseIdByInventoryId(int inventoryId);
}
