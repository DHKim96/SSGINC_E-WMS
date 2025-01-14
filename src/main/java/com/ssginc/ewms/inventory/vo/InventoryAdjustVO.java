package com.ssginc.ewms.inventory.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 재고현황 데이터를 보여주기 위해 정의한 VO 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAdjustVO {
    private int inventoryId;
    private String sectorName;
    private int productId;
    private String productName;
    private int incomeUnitPrice;
    private int outgoingUnitPrice;
    private int inventoryQuantity;
    private int realInventoryQuantity;
    private LocalDate expirationDate;
}