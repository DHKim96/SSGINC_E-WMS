package com.ssginc.ewms.inventory.service;

import com.ssginc.ewms.exception.ValueCustomException;
import com.ssginc.ewms.inventory.mapper.InventoryMapper;
import com.ssginc.ewms.inventory.vo.InventoryAdjustVO;
import com.ssginc.ewms.inventory.vo.InventoryStateVO;
import com.ssginc.ewms.sector.mapper.SectorMapper;
import com.ssginc.ewms.util.ErrorCode;
import com.ssginc.ewms.warehouse.mapper.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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
    private final SectorMapper sectorMapper;
    private final WarehouseMapper warehouseMapper;

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

    /**
     * 선택된 재고들의 sector를 이동하는 service layer 메소드
     * @param inventoryIdList    변경되어야 할 재고 번호 리스트
     * @param sectorNameList     변경되어야 할 sector 값 리스트
     * @return                   update가 실행된 row의 합
     */
    @Transactional
    public int updateSector(List<Integer> inventoryIdList, List<String> sectorNameList) {
        int count = 0;

        // sector 탐색을 위해 창고 정보 불러오기
        List<Integer> warehouseIdList = new ArrayList<>();
        for (int i = 0; i < inventoryIdList.size(); i++) {
            warehouseIdList.add(warehouseMapper.getWarehouseIdByInventoryId(inventoryIdList.get(i)));
        }

        // 존재하는 sector 인지 확인
        List<Integer> sectorIdList = new ArrayList<>();
        for (int i = 0; i < sectorNameList.size(); i++) {
            sectorIdList.add(sectorMapper.findSectorByWareHouseIdAndSectorName(warehouseIdList.get(i),
                    sectorNameList.get(i)).getSectorId());
        }
        if (sectorIdList.size() != inventoryIdList.size()) {
            throw new ValueCustomException(ErrorCode.SECTOR_NOT_EXIST);
        }

        // 이동 가능 여부 확인
        for (int i = 0; i < sectorIdList.size(); i++) {
            int inventoryId = inventoryIdList.get(i);
            int sectorId = sectorIdList.get(i);

            // sector 허용용량과 현재 적재용량. 이동하여야 할 재고용량을 비교하여 이동 가능한지 확인
            int allowCapacity = sectorMapper.getAllowCapacityBySectorId(sectorId);
            int storeCapacity = sectorMapper.getStoreCapacityBySectorId(sectorId);
            int inventoryCapacity = inventoryMapper.getInventoryCapacityByInventoryId(inventoryId);

            if (allowCapacity - storeCapacity < inventoryCapacity) {
                throw new ValueCustomException(ErrorCode.EXCEED_CAPACITY_ERROR);
            }
        }

        for (int i = 0; i < inventoryIdList.size(); i++) {
            int result = inventoryMapper.updateSector(inventoryIdList.get(i), sectorIdList.get(i));
            if (result > 0) {
                count++;
            }
        }
        return count;
    }

    @Transactional
    public int deleteInventories(List<Integer> inventoryIdList) {
        return inventoryMapper.deleteInventories(inventoryIdList);
    }
}
