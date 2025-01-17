package com.ssginc.ewms.outgoing.dto;

import lombok.Data;

@Data
public class TransportationResponseDto {
    private int transportationId; // 운송번호
    private int outgoingId; // 출고번호
    private String destination; // 목적지
    private int outgoingPrice; // 출고가격
    private String TransportationStart; // 출발 시간
    private String TransportationEnd; // 도착 예상 시간
    private int TransportationStatus; // 운송상태
}
