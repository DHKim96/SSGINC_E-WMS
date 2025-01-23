package com.ssginc.ewms.dashboard.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssginc.ewms.dashboard.service.WeatherService;
import com.ssginc.ewms.member.dto.ResponseDto;
import com.ssginc.ewms.member.vo.MemberVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("weather")
@RequiredArgsConstructor
public class WeatherApiController {


    private final WeatherService weatherService;

    @RequestMapping("")
    @ResponseBody
    public ResponseEntity<ResponseDto<JsonNode>> getWeatherInfo(HttpSession session) {

        MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

        JsonNode response = weatherService.getWeatherInfo(loginUser);

        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "날씨 정보 호출에 성공했습니다.", response));
    }

}
