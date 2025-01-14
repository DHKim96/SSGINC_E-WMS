package com.ssginc.ewms.member.controller;

import com.ssginc.ewms.member.dto.MemberInsertRequest;
import com.ssginc.ewms.member.service.RegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("registration")
public class RegisterController {

    private final RegisterService registerService;


    /**
     * 회원가입 버튼 클릭 시 현재 날짜 이후로 생년월일을 선택하지 못하게 하고자
     * 현재 날짜를 회원가입 페이지에 동적으로 매핑해줍니다.
     * @param model Model
     * @return 회원가입 페이지 주소
     */
    @GetMapping("")
    public String registration(Model model) {

        LocalDate today = LocalDate.now();

        model.addAttribute("today", today.toString());
        model.addAttribute("pastDate", today.minusYears(150));

        return "member/registration";
    }

    /**
     * 클라이언트로부터 받은 아이디를 중복 체크합니다.
     * @param id 회원 아이디
     * @return 중복 여부
     */
    @GetMapping("checkId")
    @ResponseBody
    public boolean checkId(String id) {
        return registerService.checkId(id);
    }

    /**
     * 클라이언트의 정보를 DB 서버에 저장합니다.
     * @param member 회원 정보
     * @return 대시보드 페이지
     */
    @PostMapping("register")
    public String register(MemberInsertRequest member,
                           RedirectAttributes redirectAttributes) {

        int res = 0;

        try {
            res = registerService.insertMember(member);

            if (res == 0){
                log.error("회원가입 실패");
                return "redirect:/registration";
            } else {
                redirectAttributes.addFlashAttribute("alertMsg", "회원가입에 성공했습니다.");
                return "member/login";
            }

        } catch (Exception e) {
            log.error("회원가입 실패 = {}", e.getMessage());
            return "redirect:/registration";
        }
    }
}
