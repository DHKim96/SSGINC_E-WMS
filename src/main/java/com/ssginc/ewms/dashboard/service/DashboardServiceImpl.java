package com.ssginc.ewms.dashboard.service;

import com.ssginc.ewms.dashboard.dto.IncomeResponseDto;
import com.ssginc.ewms.dashboard.dto.OutgoingResponseDto;
import com.ssginc.ewms.dashboard.mapper.DashboardMapper;
import com.ssginc.ewms.exception.DashboardException;
import com.ssginc.ewms.exception.InvalidFormatException;
import com.ssginc.ewms.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardMapper dashboardMapper;

    private final String[] types = {"daily", "monthly", "yearly"};

    @Override
    public List<IncomeResponseDto> selectIncomeListByType(String type) {

        if (type == null) {
            throw new DashboardException(ErrorCode.NULL_POINT_ERROR);
        }

        if (Arrays.stream(types).noneMatch(t -> t.equals(type))) {
            throw new DashboardException(ErrorCode.INVALID_TYPE_FORMAT);
        }

        LocalDate nowDate = LocalDate.now();

        List<IncomeResponseDto> result = dashboardMapper.selectIncomeListByType(Map.of("type", type, "nowDate", nowDate));

        if (result.isEmpty()) {
            throw new DashboardException(ErrorCode.DATA_NOT_FOUNDED);
        }

        int cumulativeIncome = 0;

        for (IncomeResponseDto dto : result) {
            cumulativeIncome += Integer.parseInt(dto.getIncomeQuantity());
            dto.setCumulativeSum(String.valueOf(cumulativeIncome));
        }

        return result;

    }

    @Override
    public List<OutgoingResponseDto> selectOutgoingListByType(String type) {
        return List.of();
    }
}
