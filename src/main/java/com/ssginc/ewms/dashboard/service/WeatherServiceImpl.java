package com.ssginc.ewms.dashboard.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssginc.ewms.dashboard.dto.GridDto;
import com.ssginc.ewms.exception.ApiWeatherException;
import com.ssginc.ewms.member.mapper.MemberMapper;
import com.ssginc.ewms.member.vo.MemberVO;
import com.ssginc.ewms.util.ErrorCode;
import com.ssginc.ewms.util.GpsTransfer;
import com.ssginc.ewms.warehouse.mapper.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private static final String END_POINT = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst";
    private final WarehouseMapper warehouseMapper;

    @Value("${weather.serviceKey.encoding}")
    private String apiEncodingKey;

    @Override
    public JsonNode getWeatherInfo(MemberVO loginUser) {

        GpsTransfer gpsTransfer = new GpsTransfer();

        LocalDateTime dateTime = LocalDateTime.now().minusHours(1);

        Map<String, BigDecimal> location = warehouseMapper.selectWarehouseLocationById(loginUser.getWarehouseId());

        GridDto grid = gpsTransfer.transfer(location.get("latitude").doubleValue(), location.get("longitude").doubleValue());

        String uriString = UriComponentsBuilder.fromHttpUrl(END_POINT)
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1000)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", formattingYyyyMMdd(dateTime))
                .queryParam("base_time", getNearestHour(dateTime))
                .queryParam("nx", grid.getX())
                .queryParam("ny", grid.getY())
                .toUriString();

        uriString += "&ServiceKey=" + apiEncodingKey; // UriComponentsBuilder 사용 시 이미 인코딩된 키를 추가 인코딩

        try {
            URL requestURL = new URL(uriString);
            HttpURLConnection urlConnection = (HttpURLConnection) requestURL.openConnection();

            urlConnection.setRequestMethod("GET");

            try (BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))) {
                StringBuilder responseTextBuilder = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    responseTextBuilder.append(line);
                }

                // JSON 문자열을 JSON 객체로 변환
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readTree(responseTextBuilder.toString());

            } finally {
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            throw new ApiWeatherException(ErrorCode.URI_REQUEST_FAILED, e);
        } catch (IOException e) {
            throw new ApiWeatherException(ErrorCode.IO_FAILED, e);
        }
    }

    /**
     * 현재 날짜를 yyyyMMdd 형식으로 변환
     */
    private String formattingYyyyMMdd(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    /**
     * 가장 가까운 정각 시간(HHmm 형식)을 반환
     */
    private String getNearestHour(LocalDateTime dateTime) {
        LocalDateTime truncatedDateTime = dateTime.minusMinutes(dateTime.getMinute()).withSecond(0).withNano(0);
        return truncatedDateTime.format(DateTimeFormatter.ofPattern("HHmm"));
    }
}
