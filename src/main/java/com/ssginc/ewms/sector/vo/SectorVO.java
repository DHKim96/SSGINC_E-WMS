package com.ssginc.ewms.sector.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SectorVO {
    private int sectorId;
    private int allowCapacity;
    private String sectorName;
    private int warehouseId;
}
