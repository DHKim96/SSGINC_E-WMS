package com.ssginc.ewms.inventory.service;

import com.ssginc.ewms.inventory.mapper.InventoryMapper;
import com.ssginc.ewms.inventory.vo.InventoryStateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 재고와 관련된 서비스 layer 클래스
 * <p>
 * 쿼리 수행 전.후로 데이터의 전처리.후처리 작업 진행
 */
@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryMapper inventoryMapper;

    /**
     * 현재 창고에 저장된 모든 재고현황을 보여주는 메소드
     * @param warehouseId   접속한 창고번호
     * @return 재고현황 VO 리스트
     */
    public List<InventoryStateVO> getProductInventory(int warehouseId) {
        return inventoryMapper.getProductInventory(warehouseId);
    }

    /**
     * 재고현황 필터링 하여 다중 검색
     * @param sectorName    공간이름
     * @param startDate     유통기한 설정 시작일
     * @param endDate       유통기한 설정 종료일
     * @param productName   상품 이름
     * @param supplierName  공급자명
     * @return              필터링된 재고현황 VO 리스트
     */
    public List<InventoryStateVO> searchInventory(String sectorName,
                                                  String startDate,
                                                  String endDate,
                                                  String productName,
                                                  String supplierName) {
        List<InventoryStateVO> list =
                inventoryMapper.searchInventory(sectorName, startDate, endDate, productName, supplierName);
        return list;
    }
}
