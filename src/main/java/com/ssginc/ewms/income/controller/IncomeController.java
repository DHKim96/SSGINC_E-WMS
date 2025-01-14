package com.ssginc.ewms.income.controller;

import com.ssginc.ewms.income.service.IncomeService;
import com.ssginc.ewms.income.vo.IncomeProductSectorWarehouseInventoryVO;
import com.ssginc.ewms.income.vo.IncomeShipperProductSuppierVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Controller
@RequestMapping("income")
@RequiredArgsConstructor
public class IncomeController {
    private final IncomeService incomeService;

    @GetMapping("incomemanagement")
    public String Incomemanagement(Model model) {
        System.out.println("=========================================입고관리 화면 요청 kjo05번 시작");
        List<IncomeShipperProductSuppierVO> expectedIncomeList = incomeService.getExpectedIncomeList();
        System.out.println(expectedIncomeList);
        System.out.println(">>>>>>>>>입고예정 리스트 받았으>>>>>>>>>>");
        model.addAttribute("ExpectedIncomeList", expectedIncomeList);

        return "income/incomemanagement";
    }

    @PostMapping("/filter")
    @ResponseBody
    public ResponseEntity<List<IncomeShipperProductSuppierVO>> filterIncome(
            @RequestBody IncomeShipperProductSuppierVO filterCriteria) {
        try {
            log.info("다중조건 쿼리 시작 KJo1");
            log.info("내가 받아버린 검색 데이터: {}", filterCriteria);
            List<IncomeShipperProductSuppierVO> filteredList = incomeService.fourFilterSelect(filterCriteria);
            log.info("다중조건 필터링 결과{}", filteredList);
            return new ResponseEntity<>(filteredList, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error("다중조건 처리중 에러 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }


    @GetMapping("/details/{incomeId}")
    @ResponseBody
    public ResponseEntity<List<IncomeShipperProductSuppierVO>> getDetails(@PathVariable int incomeId) {
        System.out.println("============================================kjo02시작");
        System.out.println("가져온 아이디========================================"+incomeId);
        try {
            List<IncomeShipperProductSuppierVO> details = incomeService.detailsSelect(incomeId);
            System.out.println("가저온 상세정보================================"+details);
            if (details == null || details.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(details);
        } catch (Exception e) {
            e.printStackTrace(); // 로그 확인을 위해 추가
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ArrayList<>()); // null 대신 빈 리스트 반환
        }
    }

    @PostMapping("/cancle")
    @ResponseBody
    public ResponseEntity<Boolean> cancel(@RequestBody Map<String, String> request) {
        try {
            System.out.println("===================================================kjo04시작");
            String[] idArray = request.get("incomeId").split(",");
            List<Integer> incomeIds = new ArrayList<>();

            for(String id : idArray) {
                incomeIds.add(Integer.parseInt(id));
            }

            System.out.println("취소리스트==================================="+incomeIds);

            boolean result = incomeService.updateCancleStatus(incomeIds) > 0;
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }






    @GetMapping("inspectionmanagement")
    public String Inspectionmanagement(Model model) {
        System.out.println("=========================================검수관리 화면요청 받았으 kjo06번시작");
        List<IncomeShipperProductSuppierVO> ExpectedNormalIncomeList = incomeService.getExpectedNormalIncomeList();
        System.out.println(ExpectedNormalIncomeList);
        System.out.println(">>>>>>>>>검수관리 입고예정 리스트 받았으>>>>>>>>>>");
        model.addAttribute("ExpectedNormalIncomeList", ExpectedNormalIncomeList);

        List<IncomeShipperProductSuppierVO> UrgentIncomeProducts = incomeService.getUrgentIncomeProducts();
        System.out.println("=========================================검수관리 화면요청 받았으 kjo07번시작");
        System.out.println("========================================긴급리스트"+UrgentIncomeProducts);
        model.addAttribute("UrgentIncomeProducts", UrgentIncomeProducts);

        List<IncomeShipperProductSuppierVO> underReviewList = incomeService.getUnderReviewList();
        System.out.println("=========================================검수관리 화면요청 받았으 kjo09번시작");
        System.out.println("========================================입고중 리스트"+underReviewList);
        model.addAttribute("UnderReviewList", underReviewList);

        return "income/inspectionmanagement";

    }

    @PostMapping("/updateUnderReview")
    @ResponseBody
    public ResponseEntity<Boolean> updateUnderReview(@RequestBody List<Integer> incomeIds) {
        try {
            log.info("kjo-10번 시작>>>>>>>>>>>>>");
            System.out.println("검수 진행 요청 보기: " + incomeIds);
            log.info("검수 진행 요청 보기:{} " , incomeIds);
            // Service 단으로 넘어가서 updateUnderReview 수행
            int result = incomeService.updateUnderReview(incomeIds);
            log.info("검수중 상태변경 처리 결과:{} " , result);
            return ResponseEntity.ok(result > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping("/updateActualQuantityAndStatus")
    @ResponseBody
    public ResponseEntity<Boolean> updateActualQuantityAndStatus(@RequestBody IncomeShipperProductSuppierVO vo) {
        log.info("updateActualQuantityAndStatus vo : {}", vo);
        int result = incomeService.updateActualQuantityAndStatus(vo);
        // 업데이트 성공 여부 반환
        if (result > 0) {
            log.info("업데이트성공");
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            log.error("업데이트 실패");
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }





    @GetMapping("accumulationmanagement")
    public String Accumulationmanagement(Model model) {
        System.out.println("=========================================적치관리 화면 요청 Kjo-13시작");
        List<IncomeProductSectorWarehouseInventoryVO> storageInProgressList = incomeService.getStorageInProgressList();
        System.out.println("========================================적치관리 리스트 리스트"+storageInProgressList);
        model.addAttribute("StorageInProgressList", storageInProgressList);

        return "/income/accumulationmanagement";
    }

    @PostMapping("/warehouseIds")
    @ResponseBody
    public ResponseEntity<List<Integer>> getWarehouseIds() {
        try {
            System.out.println("==========================================Kjo-18시작");
            List<Integer> warehouseIds = incomeService.selectWarehouseId(); // kj0-18
            System.out.println("내가 가져온 창고 아이디==========="+warehouseIds);
            return ResponseEntity.ok(warehouseIds);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/sectorIds")
    @ResponseBody
    public ResponseEntity<List<IncomeProductSectorWarehouseInventoryVO>> getSectorIds() {
        try {
            System.out.println("==========================================Kjo-15시작");
            List<IncomeProductSectorWarehouseInventoryVO> sectorIds = incomeService.getWarehouseSectorList(); // kj0-18
            System.out.println("내가 가져온 창고에 해당하는 섹터 아이디==========="+sectorIds);
            return ResponseEntity.ok(sectorIds);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/sectorCapacity")
    @ResponseBody
    public ResponseEntity<List<IncomeProductSectorWarehouseInventoryVO>> getSectorCapacity(@RequestBody Map<String, Integer> request) {
        try {
            System.out.println("==========================================Kjo-16시작");
            int sectorId = request.get("sectorId");
            System.out.println("내가 가져온 창고에 해당하는 섹터 아이디==========="+sectorId);
            List<IncomeProductSectorWarehouseInventoryVO> capacity = incomeService.getSectorAvailableCapacity(sectorId);
            log.info("적재 가능 용량 = {}",capacity);
            return ResponseEntity.ok(capacity);
        } catch (Exception e) {
            log.error("적재중 에러 발생={}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/InspectionCapacity")
    @ResponseBody
    public ResponseEntity<List<IncomeProductSectorWarehouseInventoryVO>> getInspectionCapacity(
            @RequestBody Map<String, Integer> request) {
        try {
            System.out.println("kjo-17시작>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            // 클라이언트로부터 넘어온 JSON의 "productId" 값을 가져옴
            int productId = request.get("productId");
            System.out.println("넘어온 productID>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+productId);

            // myBatis 매퍼 호출 (service > mapper)
            List<IncomeProductSectorWarehouseInventoryVO> list = incomeService.getInspectionCapacity(productId);
            System.out.println("넘어온 리스트>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+list);

            return ResponseEntity.ok(list);
        }
       catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
       }
    }


    @PostMapping("/updateStatus")
    @ResponseBody
    public ResponseEntity<Boolean> updateIncomeStatus(@RequestBody Map<String, Integer> request) {
        try {
            System.out.println("kjo-19시작>>>>>>>>>>>>>>>>>>>");
            int productId = request.get("productId");
            int result = incomeService.updateIncomeStatusComplete(productId);
            log.info("현재 업데이트상태>>>>>>>>>{}",result);
            return ResponseEntity.ok(result > 0);
        } catch (Exception e) {
            log.error("입고상태 업데이트 중 에러 발생: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }









}