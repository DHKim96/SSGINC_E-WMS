package com.ssginc.ewms.outgoing.mapper;

import com.ssginc.ewms.outgoing.vo.OutgoingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OutgoingMapper {
    List<OutgoingVO> getOutgoingList();
    List<OutgoingVO> selectByDateRange(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
}