package com.ssginc.ewms.dashboard.dto;

import lombok.Data;

@Data
public class OutgoingResponseDto {
    private String outgoingQuantity;
    private String outgoingDate;
    private String cumulativeSum; // 누적합
    private String branchName; // 지점명
}
