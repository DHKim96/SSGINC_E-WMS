package com.ssginc.ewms.dashboard.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface WeatherService {
    JsonNode getWeatherInfo();
}
