package com.ssginc.ewms.sector.mapper;

import com.ssginc.ewms.sector.vo.SectorVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SectorMapper {
    /**
     * 창고아이디와 공간 이름에 기반하여 sector 정보를 불러오는 함수
     * @param warehouseId   창고 아이디
     * @param sectorName    공간 이름
     * @return              현재 sector에 저장된 총 적재용량
     */
    SectorVO findSectorByWareHouseIdAndSectorName(int warehouseId, String sectorName);

    /**
     * 현재 sector에 허용용량을 가져오는 함수
     * @param sectorId   변경을 위한 재고아이디 (대상)
     * @return           현재 sector의 허용용량
     */
    int getAllowCapacityBySectorId(int sectorId);

    /**
     * 현재 sector에 저장된 재고용량을 가져오는 함수
     * @param sectorId   변경을 위한 재고아이디 (대상)
     * @return           현재 sector에 저장된 총 적재용량
     */
    Integer getStoreCapacityBySectorId(int sectorId);

    SectorVO findSectorByName(String sectorName);
}
