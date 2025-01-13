package com.ssginc.ewms.member.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("modify")
@RequiredArgsConstructor
public class ModifyController {

    @GetMapping("verify")
    public String verify() {
        return "member/verify";
    }

    @GetMapping("")
    public String modifyMember(HttpSession session) {
        Long authExpiry = (Long) session.getAttribute("authExpiration");

        if (authExpiry == null || System.currentTimeMillis() > authExpiry) {
            session.invalidate();
            return "redirect:/modify/verify"; // 인증 만료 시 인증 페이지로 이동
        }

        return "member/modify";
    }
}
