package com.ssginc.ewms.inventory.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAdjustVO {
    private int inventoryId;
    private String sectorName;
    private int productId;
    private String productName;
    private int incomePrice;
    private int outgoingPrice;
    private int inventoryQuantity;
    private int realInventoryQuantity;
    private LocalDate expirationDate;
}