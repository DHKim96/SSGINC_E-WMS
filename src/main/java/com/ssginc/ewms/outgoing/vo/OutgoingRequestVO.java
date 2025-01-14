package com.ssginc.ewms.outgoing.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutgoingRequestVO {
    private int productId;
    private int shipperId;
    private int outgoingStatus;
    private int outgoingType;
    private int outgoingQuantity;
    private int outgoingPrice;
    private int branchId;
    private int sectorId;
}

