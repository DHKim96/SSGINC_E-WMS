package com.ssginc.ewms.dashboard.dto;

import lombok.Data;

@Data
public class IncomeResponseDto {
    private String incomeDate; // 입고일
    private String incomeQuantity; // 입고 수량
    private String cumulativeSum; // 누적합
}
