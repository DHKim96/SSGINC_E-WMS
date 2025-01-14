package com.ssginc.ewms.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVO {
    private int productId;
    private String productName;
    private int incomeUnitPrice;
    private int outgoingUnitPrice;
    private int safetyQuantity;
    private int supplierId;
    private int discontinuedStatus;
    private String productUnit;
    private int unitCapacity;
    private int categoryId;
}
