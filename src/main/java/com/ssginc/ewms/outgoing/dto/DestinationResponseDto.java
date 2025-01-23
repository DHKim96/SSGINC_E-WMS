package com.ssginc.ewms.outgoing.dto;

import lombok.Data;

@Data
public class DestinationResponseDto {
    private String warehouseLatitude; // 출발지 위도
    private String warehouseLongitude; // 출발지 경도
    private String branchLatitude; // 도착지 위도
    private String branchLongitude; // 도착지 경도
}
