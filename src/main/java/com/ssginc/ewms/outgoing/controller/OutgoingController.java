package com.ssginc.ewms.outgoing.controller;

import com.ssginc.ewms.outgoing.service.OutgoingService;
import com.ssginc.ewms.outgoing.vo.OutgoingVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("outgoing")
public class OutgoingController {

    private final OutgoingService outgoingService;

    public OutgoingController(OutgoingService outgoingService) {
        this.outgoingService = outgoingService;
    }

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
        List<OutgoingVO> outgoingList = outgoingService.getOutgoingBySearch(null, null, null, null);
        model.addAttribute("outgoingList", outgoingList);
        return "outgoing/picking";
    }
}