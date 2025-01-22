package com.ssginc.ewms.dashboard.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssginc.ewms.member.vo.MemberVO;

public interface WeatherService {
    JsonNode getWeatherInfo(MemberVO memberVO);
}
