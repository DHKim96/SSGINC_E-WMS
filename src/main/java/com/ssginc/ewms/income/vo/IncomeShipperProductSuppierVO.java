package com.ssginc.ewms.income.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeShipperProductSuppierVO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//kjo-01 ~ kjo 12
    private String statusText;

    //Income 테이블
    private int incomeId; // income
    private int incomeStatus; // income
    private LocalDate incomeExpectedDate; // income
    private int incomePrice; // income
    private int incomeExpectedQuantity; // income
    private int incomeType; // income
    private int actualQuantity; // income


    //Shipper테이블
    private String shipperName; // shipper
    private int shipperId; // shipper

    //Supplier테이블 공급자
    private String supplierName; // supplier
    private int supplierId; // supplier

    //poduct테이블
    private String productName; // product
    private int productId; // product
    private String productUnit; // product



}
