package com.ssginc.ewms.outgoing.controller;

import com.ssginc.ewms.outgoing.service.OutgoingService;
import com.ssginc.ewms.outgoing.vo.OutgoingVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<OutgoingVO> outgoingList = outgoingService.getOutgoingList();
        model.addAttribute("outgoingList", outgoingList);
        return "outgoing/outgoing";
    }

    @GetMapping("/searchByDate")
    public ResponseEntity<List<OutgoingVO>> searchByDate(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        try {
            List<OutgoingVO> outgoingList = outgoingService.getOutgoingByDateRange(startDate, endDate);
            return ResponseEntity.ok(outgoingList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}