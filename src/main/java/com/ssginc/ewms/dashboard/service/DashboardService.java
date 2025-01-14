package com.ssginc.ewms.dashboard.service;

import com.ssginc.ewms.dashboard.dto.IncomeResponseDto;
import com.ssginc.ewms.dashboard.dto.OutgoingResponseDto;

import java.util.List;

/**
 * 대시보드 관련 기능 수행하는 서비스
 */
public interface DashboardService {

    /**
     * 선택한 기간별 입고량을 반환합니다.
     * @param type daily / monthly / yearly
     * @return 입고량
     */
    List<IncomeResponseDto> selectIncomeListByType(String type);

    /**
     * 선택한 기간별 출고량을 반환합니다.
     * @param type daily / monthly / yearly
     * @return 기간별 출고량
     */
    List<OutgoingResponseDto> selectOutgoingListByType(String type);


    /**
     * 선택한 기간의 최다 출고 지점 5개를 조회합니다.
     * @param year 연도
     * @param month 월
     * @param day 일
     * @return 최다 출고 지점 및 출고량 담은 dto
     */
    List<OutgoingResponseDto> selectTopOutgoingBranchesByDate(String year, String month, String day);
}
