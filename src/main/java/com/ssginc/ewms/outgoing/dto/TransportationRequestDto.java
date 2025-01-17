package com.ssginc.ewms.outgoing.dto;

import lombok.Data;

@Data
public class TransportationRequestDto {
    private int outgoingId; // 출고번호
    private String TransportationStart; // 출발 시간
    private String TransportationEnd; // 도착 예상 시간
}
