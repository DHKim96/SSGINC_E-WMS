package com.ssginc.ewms.dashboard.controller;

import com.ssginc.ewms.dashboard.dto.IncomeResponseDto;
import com.ssginc.ewms.dashboard.dto.OutgoingResponseDto;
import com.ssginc.ewms.dashboard.service.DashboardService;
import com.ssginc.ewms.member.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("")
    public String dashboard() {
        return "dashboard/dashboard";
    }


    @GetMapping("chart/income/{type}")
    @ResponseBody
    public ResponseEntity<ResponseDto<List<IncomeResponseDto>>> income(@PathVariable("type") String type) {

        List<IncomeResponseDto> list = dashboardService.selectIncomeListByType(type);

        log.info("type = {}", type);
        log.info("입고 데이터 - {}", list.size());

        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "입고 데이터 조회에 성공했습니다.", list));
    }

    @GetMapping("chart/outgoing/{type}")
    @ResponseBody
    public ResponseEntity<ResponseDto<List<OutgoingResponseDto>>> outgoing(@PathVariable("type") String type) {

        List<OutgoingResponseDto> list = dashboardService.selectOutgoingListByType(type);

        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "출고 데이터 조회에 성공했습니다.", list));
    }
}
