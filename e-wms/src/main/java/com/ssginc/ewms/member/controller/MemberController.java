package com.ssginc.ewms.member.controller;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.service.MemberService;
import com.ssginc.ewms.member.vo.MemberVO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
