package com.ssginc.ewms.income.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeProductSectorWarehouseInventoryVO {
//kjo-13 ~ kjo 19

    // income 테이블
    private int incomeId;               // income.income_id
    private int incomeStatus;           // income.income_status
    private int actualQuantity;         // income.actual_quantity
    private int incomeExpectedQuantity; // income.income_expected_quantity

    // product 테이블
    private int productId;       // product.product_id
    private String productName;  // product.product_name
    private String productUnit;  // product.product_unit
    private int unitCapacity;    // product.unit_capacity

    // sector 테이블
    private int sectorId;        // sector.sector_id
    private int allowCapacity;   // sector.allow_capacity

    // warehouse 테이블
    private int warehouseId;     // warehouse.warehouse_id

    // inventory 테이블
    private int inventoryQuantity; // inventory.inventory_quantity

    private int availableCapacity;
    private int allocatedCapacity;

}
