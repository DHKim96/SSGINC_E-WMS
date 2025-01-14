package com.ssginc.ewms.income.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IncomeFormVO {
    private String productName;
    private String sectorName;
    private String supplierName;
    private String supplierEmail;
    private String shipperName;
    private String incomeType;
    private int incomeQuantity;
    private String incomeExpectedDate;
}

