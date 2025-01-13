package com.ssginc.ewms.member.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(HttpSession session) {
        if (session.getAttribute("loginUser") != null) {
            // 로그인된 상태라면 대시보드로 리다이렉트
            return new ModelAndView("redirect:/dashboard");
        }
        // 로그인되지 않은 상태라면 로그인 페이지로 이동
        return new ModelAndView("member/login");
    }
}
