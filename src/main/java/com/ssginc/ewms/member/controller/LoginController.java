package com.ssginc.ewms.member.controller;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.service.LoginService;
import com.ssginc.ewms.member.service.MemberService;
import com.ssginc.ewms.member.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("login")
public class LoginController {

    private final LoginService loginService;
    private final MemberService memberService;

    @PostMapping("/login")
    public String login(MemberRequest member,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        MemberVO loginUser = memberService.selectMemberById(member);

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

    @GetMapping("/findId")
    @ResponseBody
    public boolean findId(String email){
        return loginService.findId(email);
    }

    @GetMapping("/checkEmail")
    @ResponseBody
    public boolean checkEmail(String id, String email) {
        return email.equals(memberService.selectMemberEmailById(id));
    }

    @GetMapping("/findPw")
    @ResponseBody
    public boolean findPw(String id){
        return loginService.findPw(id);
    }



}
