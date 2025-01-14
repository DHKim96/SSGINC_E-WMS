package com.ssginc.ewms.outgoing.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class OutgoingFormVO {
    private String productName;
    private String sectorName;
    private String shipperName;
    private String outgoingType;
    private String branchName;
    private int outgoingQuantity;
}
