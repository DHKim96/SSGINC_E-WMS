package com.ssginc.ewms.outgoing.dto;

import lombok.Data;

@Data
public class TransportationRequestDto {
    private int outgoingId; // 출고번호
    private String transportationStart; // 출발 시간
    private String transportationEnd; // 도착 예상 시간
}
