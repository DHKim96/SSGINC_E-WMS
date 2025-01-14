package com.ssginc.ewms.outgoing.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OutgoingVO {
    private int outgoingId;
    private int outgoingQuantity;
    private String outgoingDate;
    private int outgoingStatus;
    private String branchName;
    private int productId;
    private String productName;
    private int shipperId;
    private int sectorId;
    private int totalPrice;
    private int unitPrice;
    private int inventoryQuantity;
}
