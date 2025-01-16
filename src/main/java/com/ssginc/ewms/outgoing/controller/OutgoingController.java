package com.ssginc.ewms.outgoing.controller;

import com.ssginc.ewms.branch.service.BranchService;
import com.ssginc.ewms.branch.vo.BranchVO;
import com.ssginc.ewms.outgoing.service.OutgoingServiceImpl;
import com.ssginc.ewms.outgoing.vo.OutgoingFormVO;
import com.ssginc.ewms.outgoing.vo.OutgoingVO;
import com.ssginc.ewms.poi.PoiServiceImpl;
import com.ssginc.ewms.shipper.service.ShipperService;
import com.ssginc.ewms.shipper.vo.ShipperVO;
import com.ssginc.ewms.smtp.service.SmtpService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("outgoing")
public class OutgoingController {
    private final PoiServiceImpl poiService;
    private final SmtpService smtpService;
    private final ShipperService shipperService;
    private final BranchService branchService;

    private final OutgoingServiceImpl outgoingService;

    @GetMapping("/outgoing")
    public String outgoing(Model model) {
        List<OutgoingVO> outgoingList = outgoingService.getOutgoingBySearch(null, null, null, null);
        model.addAttribute("outgoingList", outgoingList);
        return "outgoing/outgoing";
    }

    @GetMapping("/searchByDate")
    public ResponseEntity<List<OutgoingVO>> searchByDate(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("productName") String productName,
            @RequestParam("productStatus") String productStatus) {
        try {
            List<OutgoingVO> outgoingList = outgoingService.getOutgoingBySearch(startDate, endDate, productName, productStatus);
            return ResponseEntity.ok(outgoingList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<String> approveOutgoing(@RequestParam("outgoingId") int outgoingId) {
        try {
            outgoingService.updateOutgoingStatus(outgoingId, 1); // 상태를 1로 업데이트
            return ResponseEntity.ok("outgoing/picking");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("출고 상태 업데이트 실패");
        }
    }

    @GetMapping("/picking")
    public String picking(Model model) {
        List<OutgoingVO> outgoingList = outgoingService.getOutgoingWithInventory(null, null, null, null);
        model.addAttribute("outgoingList", outgoingList);
        return "outgoing/picking";
    }

    @GetMapping("/searchByPickingDate")
    public ResponseEntity<List<OutgoingVO>> searchByPickingDate(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("productName") String productName,
            @RequestParam("productStatus") String productStatus) {
        try {
            List<OutgoingVO> outgoingList = outgoingService.getOutgoingWithInventory(startDate, endDate, productName, productStatus);
            return ResponseEntity.ok(outgoingList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/approvePicking")
    public ResponseEntity<String> approvePicking(@RequestParam("outgoingId") int outgoingId) {
        try {
            outgoingService.updateOutgoingStatusAndQuantity(outgoingId, 2); // 상태를 출고 완료로 업데이트
            return ResponseEntity.ok("출고 상태와 재고가 성공적으로 업데이트되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("출고 상태 또는 재고 업데이트 실패");
        }
    }

    @GetMapping("/complete")
    public String complete(Model model) {
        List<OutgoingVO> outgoingList = outgoingService.getOutgoingWithInventory(null, null, null, null);
        model.addAttribute("outgoingList", outgoingList);
        return "outgoing/complete";
    }

    @GetMapping("register/{inventoryId}")
    public String displayOutgoingInfo(@PathVariable int inventoryId, Model model) {
        OutgoingFormVO outgoingForm = outgoingService.getOutgoingFormByInventoryId(inventoryId);
        List<ShipperVO> shipperList = shipperService.findShipperList();
        List<BranchVO> branchList = branchService.findBranchList();

        model.addAttribute("outgoingForm", outgoingForm);
        model.addAttribute("shippers", shipperList);
        model.addAttribute("branches", branchList);
        return "/outgoing/register";
    }

    @PostMapping("register")
    public String registerOutgoing(OutgoingFormVO outgoingForm) {
        int result = outgoingService.insertOutgoingRequest(outgoingForm);

        if (result == 1) {
            try {
                ModelAndView modelAndView = poiService.makeOutgoingFile(outgoingForm);
                smtpService.sendRequest(1, "kdc9619@naver.com", "attach/outgoing.docx"
                        , "outgoing.docx");
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }

        return "redirect:/inventory/inventory/1";
    }
}