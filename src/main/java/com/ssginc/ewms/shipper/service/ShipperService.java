package com.ssginc.ewms.shipper.service;

import com.ssginc.ewms.shipper.vo.ShipperVO;

import java.util.List;

public interface ShipperService {
    List<ShipperVO> findShipperList();
}
