package com.ssginc.ewms.member.controller;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.service.LoginService;
import com.ssginc.ewms.member.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("")
    public String login(MemberRequest member,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        log.info("로그인 객체 = {}", member);

        MemberVO loginUser = loginService.selectMemberById(member);

        if (loginUser == null) {
            // RedirectAttributes 는 현재 요청에서만 유효
            redirectAttributes.addFlashAttribute("alertMsg", "아이디가 일치하지 않습니다.");
            return "redirect:/"; // 리다이렉트하면서 메시지 전달
        } else if (!loginService.validatePwd(member, loginUser)) {
            redirectAttributes.addFlashAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
            return "redirect:/";
        }

        // 로그인 성공
        session.setAttribute("loginUser", loginUser);
        return "dashboard/dashboard"; // 성공 시 대시보드로 이동
    }
}
