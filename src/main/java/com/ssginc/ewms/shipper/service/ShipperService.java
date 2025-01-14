package com.ssginc.ewms.shipper.service;

import com.ssginc.ewms.shipper.mapper.ShipperMapper;
import com.ssginc.ewms.shipper.vo.ShipperVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperService {
    private final ShipperMapper shipperMapper;

    public List<ShipperVO> findShipperList() {
        return shipperMapper.findShipperList();
    }
}
