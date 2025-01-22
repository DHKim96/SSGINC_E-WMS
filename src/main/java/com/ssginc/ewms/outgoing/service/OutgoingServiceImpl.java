package com.ssginc.ewms.outgoing.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssginc.ewms.branch.mapper.BranchMapper;
import com.ssginc.ewms.branch.vo.BranchVO;
import com.ssginc.ewms.exception.ApiKakaoNaviException;
import com.ssginc.ewms.exception.OutgoingFailedException;
import com.ssginc.ewms.exception.ValueCustomException;
import com.ssginc.ewms.outgoing.dto.DestinationResponseDto;
import com.ssginc.ewms.outgoing.dto.TransportationRequestDto;
import com.ssginc.ewms.outgoing.mapper.OutgoingMapper;
import com.ssginc.ewms.outgoing.vo.OutgoingFormVO;
import com.ssginc.ewms.outgoing.vo.OutgoingRequestVO;
import com.ssginc.ewms.outgoing.vo.OutgoingVO;
import com.ssginc.ewms.product.mapper.ProductMapper;
import com.ssginc.ewms.product.vo.ProductVO;
import com.ssginc.ewms.sector.mapper.SectorMapper;
import com.ssginc.ewms.sector.vo.SectorVO;
import com.ssginc.ewms.shipper.mapper.ShipperMapper;
import com.ssginc.ewms.shipper.vo.ShipperVO;
import com.ssginc.ewms.util.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class OutgoingServiceImpl implements OutgoingService {
    private final ProductMapper productMapper;
    private final ShipperMapper shipperMapper;
    private final BranchMapper branchMapper;
    private final SectorMapper sectorMapper;

    private final OutgoingMapper outgoingMapper;

    @Value("${kakao.mobility.restApi.key}")
    private String kakaoRestApiKey;

    @Override
    public List<OutgoingVO> getOutgoingBySearch(String startDate, String endDate, String productName, String productStatus) {
        return outgoingMapper.getOutgoingList(startDate, endDate, productName, productStatus);
    }

    @Override
    public void updateOutgoingStatus(int outgoingId, int status) {
        outgoingMapper.updateOutgoingStatus(outgoingId, status);
    }
    
    @Override
    public List<OutgoingVO> getOutgoingWithInventory(String startDate, String endDate, String productName, String productStatus) {
        return outgoingMapper.getOutgoingWithInventory(startDate, endDate, productName, productStatus);
    }

    @Transactional
    @Override
    public void updateOutgoingStatusAndQuantity(int outgoingId, int status) {
        // 재고 검증
        Integer inventoryQuantity = outgoingMapper.getInventoryQuantity(outgoingId);
        Integer outgoingQuantity = outgoingMapper.getOutgoingQuantity(outgoingId);

        if (inventoryQuantity == null || outgoingQuantity == null) {
            throw new IllegalStateException("유효하지 않은 ID입니다.");
        }

        // 재고 부족 시 예외 발생
        if (inventoryQuantity < outgoingQuantity) {
            throw new IllegalStateException("재고가 부족합니다.");
        }

        // 상태 업데이트
        outgoingMapper.updateOutgoingStatus(outgoingId, status);

        // 재고 업데이트
        outgoingMapper.updateQuantity(outgoingId);

        // 날짜 업데이트
        String currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        outgoingMapper.updateOutgoingDate(outgoingId, currentDateTime); // 상태도 함께 업데이트

        DestinationResponseDto location = outgoingMapper.selectLocationInfo(outgoingId);

        TransportationRequestDto transportationRequestDto = new TransportationRequestDto();

        int arrivalTime = getArrivalTime(location);

        arrivalTime += (arrivalTime / 3600) * 10; // 1시간 당 휴식 시간 10분 보장

        LocalDateTime now = LocalDateTime.now();

        transportationRequestDto.setOutgoingId(outgoingId);
        transportationRequestDto.setTransportationStart(String.valueOf(now));
        transportationRequestDto.setTransportationEnd(String.valueOf( now.plusSeconds(arrivalTime)));

        outgoingMapper.insertTransportation(transportationRequestDto);
    }
    @Override
    public OutgoingFormVO getOutgoingFormByInventoryId(int inventoryId) {
        return outgoingMapper.getOutgoingFormByProductId(inventoryId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertOutgoingRequest(OutgoingFormVO outgoingForm) {
        OutgoingRequestVO outgoingRequestVO = new OutgoingRequestVO();

        ProductVO productVO = productMapper.getProductByName(outgoingForm.getProductName());
        ShipperVO shipperVO = shipperMapper.getShipperByName(outgoingForm.getShipperName());
        BranchVO branchVO = branchMapper.getBranchByName(outgoingForm.getBranchName());
        SectorVO sectorVO = sectorMapper.findSectorByName(outgoingForm.getSectorName());

        System.out.println(productVO);
        System.out.println(shipperVO);
        System.out.println(branchVO);
        System.out.println(sectorVO);

        if (productVO == null || branchVO == null || sectorVO == null || shipperVO == null) {
            throw new ValueCustomException(ErrorCode.NULL_POINT_ERROR);
        }


        outgoingRequestVO.setProductId(productVO.getProductId());
        outgoingRequestVO.setShipperId(shipperVO.getShipperId());
        if (outgoingForm.getOutgoingType().equals("normalOutgoing")) {
            outgoingRequestVO.setOutgoingType(0);
            outgoingRequestVO.setOutgoingStatus(0);
        } else if (outgoingForm.getOutgoingType().equals("emergencyOutgoing")) {
            outgoingRequestVO.setOutgoingType(1);
            outgoingRequestVO.setOutgoingStatus(1);
        }
        outgoingRequestVO.setOutgoingQuantity(outgoingForm.getOutgoingQuantity());
        outgoingRequestVO.setOutgoingPrice(productVO.getOutgoingUnitPrice() * outgoingForm.getOutgoingQuantity());
        outgoingRequestVO.setBranchId(branchVO.getBranchId());
        outgoingRequestVO.setSectorId(sectorVO.getSectorId());

        System.out.println(outgoingRequestVO);

        int outgoingId = outgoingMapper.insertOutgoingRequest(outgoingRequestVO);

        if (outgoingId <= 0) {
            throw new OutgoingFailedException(ErrorCode.OUTGOING_INSERT_FAILED);
        }

        return outgoingId;
    }

    /**
     * 카카오 길찾기 API 를 통해 도착 지점까지의 소요 시간을 조회하는 메서드입니다.
     * @param location 출발지와 도착지의 위도/경도를 담은 DTO
     * @return 출발지에서 도착지까지의 소요 시간(초)
     */
    private int getArrivalTime(DestinationResponseDto location) {
        int result = 0;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        String uriString = UriComponentsBuilder.fromHttpUrl("https://apis-navi.kakaomobility.com/v1/directions")
                .queryParam("origin", location.getWarehouseLongitude() + "," + location.getWarehouseLatitude())
                .queryParam("destination", location.getBranchLongitude() + "," + location.getBranchLatitude())
                .queryParam("summary", true)
                .toUriString();

        try {
            log.info("Request URL: {}", uriString);
            ResponseEntity<String> response = restTemplate.exchange(uriString, HttpMethod.GET, entity, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            log.info("rootNode = {}", rootNode.path("routes").path(0).path("summary").path("duration"));

            result = rootNode.path("routes").path(0).path("summary").path("duration").asInt();

        } catch (RestClientException e) {
            throw new ApiKakaoNaviException(ErrorCode.API_KAKAO_REST_ERROR, e);
        } catch (JsonProcessingException e) {
            throw new ApiKakaoNaviException(ErrorCode.API_KAKAO_JSON_MAPPING_FAILED, e);
        }

        return result;
    }

    @Override
    public OutgoingFormVO getOutgoingFormByProductId(int productId) {
        return outgoingMapper.getOutgoingFormByProductId(productId);
    }
}