package com.ssginc.ewms.shipper.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipperVO {
    private int shipperId;
    private String shipperName;
    private String shipperBusinessNumber;
    private String shipperRepresentativeName;
    private String shipperEmail;
}
