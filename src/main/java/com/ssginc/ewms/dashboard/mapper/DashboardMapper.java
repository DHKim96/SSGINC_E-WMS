package com.ssginc.ewms.dashboard.mapper;

import com.ssginc.ewms.dashboard.dto.IncomeResponseDto;
import com.ssginc.ewms.dashboard.dto.OutgoingResponseDto;
import com.ssginc.ewms.dashboard.dto.SectorResponseDto;
import com.ssginc.ewms.dashboard.dto.TransportationResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardMapper {

    List<IncomeResponseDto> selectIncomeListByType(String type);

    List<OutgoingResponseDto> selectOutgoingListByType(@Param("type")String type);

    List<OutgoingResponseDto> selectTopOutgoingBranchesByDate(Map<String, String> year);

    List<SectorResponseDto> selectSectorListByWarehouseId(int warehouseId);

    List<TransportationResponseDto> selectTransportationList();
}
