package com.ssginc.ewms.error;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("error")
public class ErrorController {
    @GetMapping("")
    public String errorPage(@RequestParam(required = false, defaultValue = "알 수 없는 오류가 발생했습니다.") String message, Model model) {
        model.addAttribute("message", message);
        return "error/error";
    }
}
