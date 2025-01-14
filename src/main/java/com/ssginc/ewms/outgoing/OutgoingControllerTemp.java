package com.ssginc.ewms.outgoing;

import com.ssginc.ewms.branch.service.BranchService;
import com.ssginc.ewms.branch.vo.BranchVO;
import com.ssginc.ewms.outgoing.service.OutgoingService;
import com.ssginc.ewms.outgoing.vo.OutgoingFormVO;
import com.ssginc.ewms.poi.PoiService;
import com.ssginc.ewms.shipper.mapper.ShipperMapper;
import com.ssginc.ewms.shipper.service.ShipperService;
import com.ssginc.ewms.shipper.vo.ShipperVO;
import com.ssginc.ewms.smtp.service.SmtpService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("outgoing")
@RequiredArgsConstructor
public class OutgoingControllerTemp {
    private final OutgoingServiceTemp outgoingService;
    private final PoiService poiService;
    private final SmtpService smtpService;
    private final ShipperService shipperService;
    private final BranchService branchService;

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