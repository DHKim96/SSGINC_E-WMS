package com.ssginc.ewms.member.controller;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.service.MemberService;
import com.ssginc.ewms.member.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("login")
    public ModelAndView login(MemberRequest member,
                              HttpSession session,
                              ModelAndView mv) {

        log.info("Member = {}", member);

        MemberVO loginUser = memberService.selectMemberById(member);

        if (loginUser == null) {
            mv.addObject("result", "아이디가 일치하지 않습니다.");
            mv.setViewName("redirect:/");
        } else if(!memberService.validatePwd(member, loginUser)) {
            mv.addObject("result", "비밀번호가 일치하지 않습니다.");
            mv.setViewName("redirect:/");
        } else { // 성공
            session.setAttribute("loginUser", loginUser);
            mv.setViewName("dashboard/dashboard");
        }

        log.info("loginUser = {}", loginUser);

        return mv;
    }

    @GetMapping("registration")
    public String registration(Model model) {

        model.addAttribute("nowDate", LocalDate.now().toString());

        return "member/registration";
    }

    @GetMapping("registration/checkEmail")
    @ResponseBody
    public boolean checkEmail(String email) {
        return memberService.checkEmail(email) == 1;
    }

    @PostMapping("registration/authEmail")
    @ResponseBody
    public boolean authEmail(@RequestBody Map<String, String> resultMap) {
        String email = resultMap.get("email");
        String authNo = resultMap.get("authNo");
        return memberService.authEmail(email, authNo);
    }
}
