package com.ssginc.ewms.dashboard.dto;

import lombok.Data;

@Data
public class SectorResponseDto {
    private String sectorId;
    private String sectorName;
    private int allowCapacity;
    private int usedCapacity;
    private int usageRatePercentage;
}
