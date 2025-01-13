package com.ssginc.ewms.inventory.service;

import com.ssginc.ewms.inventory.mapper.InventoryMapper;
import com.ssginc.ewms.inventory.vo.InventoryAdjustVO;
import com.ssginc.ewms.inventory.vo.InventoryStateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 현재 창고의 재고조정에 필요한 정보를 반환하는 메소드
     * @param warehouseId   접속한 창고번호
     * @return              재고조정을 위한 VO 리스트
     */
    public List<InventoryAdjustVO> getProductAdjustInventory(int warehouseId) {
        return inventoryMapper.getAdjustInventoryStatus(warehouseId);
    }

    /**
     * 선택된 재고들의 실사 재고량을 수정하는 service 클래스 메소드
     * @param idList             실사 재고량이 수정되어야 할 재고번호 리스트
     * @param realQuantityList   재고 아이디별 변경되어야 할 실사 재고량 리스트
     * @return                   update가 실행된 row의 총합
     */
    @Transactional
    public int updateRealInventory(List<Integer> idList, List<Integer> realQuantityList) {
        int count = 0;
        for (int i = 0; i < idList.size(); i++) {
            int result = inventoryMapper.updateRealInventory(idList.get(i), realQuantityList.get(i));

            if (result == 1) {
                count++;
            }
        }
        return count;
    }

    /**
     * 선택된 재고들의 재고량을 실사재고량으로 수정하는 service 클래스 메소드
     * @param idList             재고량이 수정되어야 할 재고번호 리스트
     * @param realQuantityList   해당 재고의 실사 재고량 리스트
     * @return                   update가 실행된 row의 총합
     */
    @Transactional
    public int updateQuantity(List<Integer> idList, List<Integer> realQuantityList) {
        int count = 0;
        for (int i = 0; i < idList.size(); i++) {
            int result = inventoryMapper.updateQuantity(idList.get(i), realQuantityList.get(i));

            if (result == 1) {
                count++;
            }
        }
        return count;
    }
}
