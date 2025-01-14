package com.ssginc.ewms.shipper.mapper;

import com.ssginc.ewms.shipper.vo.ShipperVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShipperMapper {
    List<ShipperVO> findShipperList();

    ShipperVO getShipperByName(String shipperName);
}
