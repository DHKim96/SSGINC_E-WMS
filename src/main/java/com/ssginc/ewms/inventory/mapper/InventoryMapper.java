package com.ssginc.ewms.inventory.mapper;

import com.ssginc.ewms.inventory.vo.InventoryAdjustVO;
import com.ssginc.ewms.inventory.vo.InventoryStateVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 재고와 관련된 쿼리 수행을 위해 정의한 Mapper 클래스
 * <p>
 * 각 쿼리당 메소드 하나씩 정의되어 관리됨.
 * mybatis 쿼리문은 resources/mapper 디렉토리 내에 xml 파일로 정의됨
 */
@Mapper
public interface InventoryMapper {

    /**
     * 현재 창고에 저장된 모든 재고현황을 보여주는 메소드
     * @param warehouseId   접속한 창고번호
     * @return 재고현황 VO 리스트
     */
    List<InventoryStateVO> getProductInventory(int warehouseId);


    /**
     * 재고현황 필터링 하여 다중 검색
     * @param sectorName    공간이름
     * @param startDate     유통기한 설정 시작일
     * @param endDate       유통기한 설정 종료일
     * @param productName   상품 이름
     * @param supplierName  공급자명
     * @return              필터링된 재고현황 VO 리스트
     */
    List<InventoryStateVO> searchInventory(String sectorName,
                                           String startDate,
                                           String endDate,
                                           String productName,
                                           String supplierName);

    /**
     * 재고조정 페이지에서 최초 재고 리스트 가져오기
     * @param warehouseId   창고아이디 (0은 모든 창고의 재고정보 반환)
     * @return              재고조정 활용을 위한 VO 리스트
     */
    List<InventoryAdjustVO> getAdjustInventoryStatus(int warehouseId);

    /**
     * 실사재고량을 수정하는 함수
     * @param inventoryId   변경을 위한 재고아이디 (대상)
     * @param realQuantity  변경하여야 할 실제 재고량 (변경값)
     * @return              update 수행이 된 row 수
     */
    int updateRealInventory(int inventoryId, int realQuantity);
}
