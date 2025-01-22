package com.ssginc.ewms.dashboard.controller;

import com.ssginc.ewms.dashboard.dto.IncomeResponseDto;
import com.ssginc.ewms.dashboard.dto.OutgoingResponseDto;
import com.ssginc.ewms.dashboard.dto.SectorResponseDto;
import com.ssginc.ewms.dashboard.dto.TransportationResponseDto;
import com.ssginc.ewms.dashboard.service.DashboardService;
import com.ssginc.ewms.member.dto.ResponseDto;
import com.ssginc.ewms.member.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
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
    public String dashboard(HttpSession session) {

        List<SectorResponseDto> sectors = dashboardService.selectSectorListByWarehouseId((MemberVO)session.getAttribute("loginUser"));
        List<TransportationResponseDto> transportations = dashboardService.selectTransportationList();

        session.setAttribute("sectors", sectors);
        session.setAttribute("transportations", transportations);

        return "dashboard/dashboard";
    }


    @GetMapping("chart/income/{type}")
    @ResponseBody
    public ResponseEntity<ResponseDto<List<IncomeResponseDto>>> income(@PathVariable("type") String type) {

        List<IncomeResponseDto> list = dashboardService.selectIncomeListByType(type);

        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "입고 데이터 조회에 성공했습니다.", list));
    }

    @GetMapping("chart/outgoing/{type}")
    @ResponseBody
    public ResponseEntity<ResponseDto<List<OutgoingResponseDto>>> outgoing(@PathVariable("type") String type) {

        List<OutgoingResponseDto> list = dashboardService.selectOutgoingListByType(type);

        log.info("{} 출고량 조회 수 = {}", type, list.size());

        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "출고 데이터 조회에 성공했습니다.", list));
    }


    @GetMapping("chart/top-outgoing-branches")
    @ResponseBody
    public ResponseEntity<ResponseDto<List<OutgoingResponseDto>>> getTopOutgoingBranches(
            @RequestParam("year") String year,
            @RequestParam(value = "month", required = false) String month,
            @RequestParam(value = "day", required = false) String day) {

        List<OutgoingResponseDto> result = dashboardService.selectTopOutgoingBranchesByDate(year, month, day);

        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "최다 출고 지점 조회 성공", result));
    }

}
