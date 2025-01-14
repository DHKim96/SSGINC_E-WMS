package com.ssginc.ewms.dashboard.mapper;

import com.ssginc.ewms.dashboard.dto.IncomeResponseDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardMapper {

    List<IncomeResponseDto> selectIncomeListByType(Map<String, Object> map);
}
