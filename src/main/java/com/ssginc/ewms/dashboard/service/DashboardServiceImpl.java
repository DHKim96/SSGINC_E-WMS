package com.ssginc.ewms.dashboard.service;

import com.ssginc.ewms.dashboard.dto.IncomeResponseDto;
import com.ssginc.ewms.dashboard.dto.OutgoingResponseDto;
import com.ssginc.ewms.dashboard.mapper.DashboardMapper;
import com.ssginc.ewms.exception.DashboardException;
import com.ssginc.ewms.exception.InvalidFormatException;
import com.ssginc.ewms.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DashboardMapper dashboardMapper;

    private final String[] types = {"daily", "monthly", "yearly"};

    @Override
    public List<IncomeResponseDto> selectIncomeListByType(String type) {

        if(!verifyType(type)){
            return null;
        }

        List<IncomeResponseDto> result = dashboardMapper.selectIncomeListByType(type);

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

        if (!verifyType(type)) {
            log.error("유효하지 않은 타입: {}", type);
            return null;
        }

        log.info("MyBatis Mapper 호출 시작: {}", type);

        List<OutgoingResponseDto> result = dashboardMapper.selectOutgoingListByType(type);

        if (result == null) {
            log.error("MyBatis 호출 결과 null 반환: type = {}", type);
            throw new DashboardException(ErrorCode.DATA_NOT_FOUNDED);
        }

        log.info("쿼리 결과: {}건", result.size());

        if (result.isEmpty()) {
            log.error("데이터 없음: type = {}", type);
            throw new DashboardException(ErrorCode.DATA_NOT_FOUNDED);
        }

        int cumulativeOutgoing = 0;

        for (OutgoingResponseDto dto : result) {
            cumulativeOutgoing += Integer.parseInt(dto.getOutgoingQuantity());
            dto.setCumulativeSum(String.valueOf(cumulativeOutgoing));
        }

        log.info("누적 합계 계산 완료: 최종 값 = {}", cumulativeOutgoing);

        return result;
    }

    @Override
    public List<OutgoingResponseDto> selectTopOutgoingBranchesByDate(String year, String month, String day) {

        // 매개변수 구성
        Map<String, String> params = new HashMap<>();
        params.put("year", year);
        if (month != null && !month.isEmpty()) {
            params.put("month", month);
        }
        if (day != null && !day.isEmpty()) {
            params.put("day", day);
        }

        List<OutgoingResponseDto> result = dashboardMapper.selectTopOutgoingBranchesByDate(params);

        if (result.isEmpty()) {
            throw new DashboardException(ErrorCode.DATA_NOT_FOUNDED);
        }

        return result;
    }

    private boolean verifyType(String type) {
        if (type == null) {
            throw new DashboardException(ErrorCode.NULL_POINT_ERROR);
        }

        if (Arrays.stream(types).noneMatch(t -> t.equals(type))) {
            throw new DashboardException(ErrorCode.INVALID_TYPE_FORMAT);
        }

        return true;
    }
}
