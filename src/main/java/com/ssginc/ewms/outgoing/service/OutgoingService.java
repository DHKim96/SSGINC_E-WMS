package com.ssginc.ewms.outgoing.service;

import com.ssginc.ewms.outgoing.mapper.OutgoingMapper;
import com.ssginc.ewms.outgoing.vo.OutgoingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OutgoingService {

    @Autowired
    private final OutgoingMapper outgoingMapper;

    public OutgoingService(OutgoingMapper outgoingMapper) {
        this.outgoingMapper = outgoingMapper;
    }

    public List<OutgoingVO> getOutgoingBySearch(String startDate, String endDate, String productName, String productStatus) {
        return outgoingMapper.getOutgoingList(startDate, endDate, productName, productStatus);
    }

    public void updateOutgoingStatus(int outgoingId, int status) {
        outgoingMapper.updateOutgoingStatus(outgoingId, status);
    }
    
    public List<OutgoingVO> getOutgoingWithInventory(String startDate, String endDate, String productName, String productStatus) {
        return outgoingMapper.getOutgoingWithInventory(startDate, endDate, productName, productStatus);
    }

    @Transactional
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
    }

}