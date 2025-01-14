package com.ssginc.ewms.dashboard.dto;

import lombok.Data;

@Data
public class IncomeResponseDto {
    String incomeDate; // 입고일
    String incomeQuantity; // 입고 수량
    String cumulativeSum; // 누적합
}
