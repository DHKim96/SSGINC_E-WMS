package com.ssginc.ewms.inventory.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 재고현황을 보여주기 위해 정의한 VO 클래스
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryStateVO {
    private int inventoryId;
    private String sectorName;
    private int productId;
    private String productName;
    private String categoryName;
    private String supplierName;
    private int incomeUnitPrice;
    private int outgoingUnitPrice;
    private int inventoryQuantity;
    private LocalDate expirationDate;
}
