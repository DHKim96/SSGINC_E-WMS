package com.ssginc.ewms.dashboard.dto;

import lombok.Data;

@Data
public class TransportationResponseDto {
    private int transportationId;
    private int outgoingId;
    private String transportationStart;
    private String transportationEnd;
    private String destination;
    private String price;
}
