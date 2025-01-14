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
}
