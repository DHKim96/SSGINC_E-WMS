package com.ssginc.ewms.inventory.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssginc.ewms.inventory.service.InventoryService;
import com.ssginc.ewms.inventory.vo.InventoryAdjustVO;
import com.ssginc.ewms.inventory.vo.InventoryStateVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 재고와 관련된 요청-응답을 수행하는 컨트롤러 클래스
 * <p>
 * 각 요청에 따라 method와 action 정의
 */
@Controller
@RequestMapping("inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * 재고현황의 정보를
     * @param warehouseId   접속한 창고번호
     * @param model         페이지에 보여줄 데이터
     * @return 재고현황 페이지
     */
    @GetMapping("inventory/{warehouseId}")
    public String inventory(@PathVariable int warehouseId, Model model) {
        List<InventoryStateVO> list = inventoryService.getProductInventory(warehouseId);
        model.addAttribute("inventories", list);
        return "inventory/inventory";
    }

    /**
     * 재고현황 필터링 하여 다중 검색
     * @param sectorName    공간이름
     * @param startDate     유통기한 설정 시작일
     * @param endDate       유통기한 설정 종료일
     * @param productName   상품 이름
     * @param supplierName  공급자명
     * @return              응답객체
     */
    @GetMapping("search")
    public ResponseEntity<List<InventoryStateVO>> search(
            @RequestParam("sectorName") String sectorName,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("productName") String productName,
            @RequestParam("supplierName") String supplierName) {
        try {
            List<InventoryStateVO> searchList = inventoryService.searchInventory(sectorName, startDate,
                    endDate, productName, supplierName);
            System.out.println(searchList);
            return ResponseEntity.ok(searchList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * 재고조정에서 필요한 정보들을 가져오는 컨트롤러 함수
     * @param warehouseId   접속한 창고번호
     * @param model         페이지에 보여줄 데이터
     * @return 재고조정 페이지
     */
    @GetMapping("adjust/{warehouseId}")
    public String adjust(@PathVariable int warehouseId, Model model) {
        List<InventoryAdjustVO> list = inventoryService.getProductAdjustInventory(warehouseId);
        model.addAttribute("inventories", list);
        return "inventory/adjust";
    }

    /**
     * 실사 재고량을 변경하기 위한 컨트롤러 메소드. 비동기 처리하여 클라이언트에게 결과값을 body로 전달
     * @param node   변경을 수행하여야 할 요청 데이터
     * @return       변경이 실행된 row 수 (처리가 다 되지 못했다면 0을 반환)
     */
    @PutMapping("updateRealInventory")
    @ResponseBody
    public int updateRealInventory(@RequestBody JsonNode node) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> idList = objectMapper.
                treeToValue(node.path("idList"), List.class);
        List<Integer> realQuantityList = objectMapper.
                treeToValue(node.path("realQuantityList"), List.class);

        int result = inventoryService.updateRealInventory(idList, realQuantityList);
        if (result == idList.size()) {
            return result;
        } else {
            return 0;
        }
    }

    /**
     * 재고량을 실사재고량으로 변경하기 위한 컨트롤러 메소드. 비동기 처리하여 클라이언트에게 결과값을 body로 전달
     * @param node   변경을 수행하여야 할 요청 데이터
     * @return       변경이 실행된 row 수 (처리가 다 되지 못했다면 0을 반환)
     */
    @PutMapping("adjustQuantity")
    @ResponseBody
    public int adjustQuantity(@RequestBody JsonNode node) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> idList = objectMapper.
                treeToValue(node.path("idList"), List.class);
        List<Integer> realQuantityList = objectMapper.
                treeToValue(node.path("realQuantityList"), List.class);

        int result = inventoryService.updateQuantity(idList, realQuantityList);
        if (result == idList.size()) {
            return result;
        } else {
            return 0;
        }
    }
}
