package com.ssginc.ewms.income.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeRequestVO {
    private int productId;
    private int shipperId;
    private int sectorId;
    private int incomeType;
    private int incomePrice;
    private int incomeStatus;
    private int incomeExpectedQuantity;
    private LocalDate incomeExpectedDate;
}
