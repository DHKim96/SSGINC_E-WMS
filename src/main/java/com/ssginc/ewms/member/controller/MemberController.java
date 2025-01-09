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

/**
 * 회원 관리를 수행하는 컨트롤러.
 *
 * <p>주요 기능 : </p>
 * 
 * <ul>
 *     <li>로그인</li>
 *     <li>아이디, 이메일, 전화번호 중복 체크</li>
 *     <li>SMTP 기반의 이메일 전송</li>
 *     <li>coolSMS 기반의 SMS 전송</li>
 *     <li>회원 정보 INSERT</li>
 * </ul>
 */

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("member")
public class MemberController {

    private final MemberService memberService;

    // =========================================== 로그인 ========================================================
    

    /**
     * 아이디와 비밀번호를 통해 로그인합니다.
     * @param member 회원 아이디, 비밀번호 데이터
     * @param session HTTPsession
     * @param mv ModelAndView
     * @return 로그인 결과에 따라 주소 담을 ModelAndView
     */
    @GetMapping("login")
    public ModelAndView login(MemberRequest member,
                              HttpSession session,
                              ModelAndView mv) {

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

        return mv;
    }


    @GetMapping("login/findId")
    @ResponseBody
    public boolean findId(String email){
        return memberService.findId(email);
    }

    @GetMapping("login/findPw")
    @ResponseBody
    public boolean findPw(String id){
        return memberService.findPw(id);
    }
    
    
    // =========================================== 회원가입 ========================================================
    
    /**
     * 회원가입 버튼 클릭 시 현재 날짜 이후로 생년월일을 선택하지 못하게 하고자
     * 현재 날짜를 회원가입 페이지에 동적으로 매핑해줍니다.
     * @param model Model
     * @return 회원가입 페이지 주소
     */
    @GetMapping("registration")
    public String registration(Model model) {

        model.addAttribute("nowDate", LocalDate.now().toString());

        return "member/registration";
    }

    /**
     * 이메일 중복 체크를 수행합니다.
     * @param email 회원가입 시 입력한 이메일 주소
     * @return 중복 여부값
     */
    @GetMapping("registration/checkEmail")
    @ResponseBody
    public boolean checkEmail(String email) {
        log.info("email = {}", email);
        return memberService.checkEmail(email);
    }

    /**
     * 클라이언트로부터 생성된 난수를 받아 입력받은 이메일 주소로 전송합니다.
     * @param resultMap 회원의 이메일 주소, 생성된 6자리 난수
     * @return 전송 성공 여부
     */
    @PostMapping("registration/authEmail")
    @ResponseBody
    public boolean authEmail(@RequestBody Map<String, String> resultMap) {
        String email = resultMap.get("email");
        String authNo = resultMap.get("authNo");
        return memberService.authEmail(email, authNo);
    }

    /**
     * 클라이언트로부터 받은 아이디를 중복 체크합니다.
     * @param id 회원 아이디
     * @return 중복 여부
     */
    @GetMapping("registration/checkId")
    @ResponseBody
    public boolean checkId(String id) {
        return memberService.checkId(id);
    }

    /**
     * 클라이언트로부터 받은 전화번호를 중복 체크합니다.
     * @param phone 회원 전화번호
     * @return 중복 여부
     */
    @GetMapping("registration/checkPhone")
    @ResponseBody
    public boolean checkPhone(String phone){
        return memberService.checkPhone(phone);
    }

    /**
     * 클라이언트의 전화번호와 생성된 난수를 클라이언트의 번호로 전송합니다.
     * @param resultMap 회원 전화번호, 6자리 난수
     * @return 중복 여부
     */
    @PostMapping("registration/authPhone")
    @ResponseBody
    public boolean authPhone(@RequestBody Map<String, String> resultMap) {
        String phone = resultMap.get("phone");
        String authNo = resultMap.get("authNo");
        return memberService.authPhone(phone, authNo);
    }

    /**
     * 클라이언트의 정보를 DB 서버에 저장합니다.
     * @param member 회원 정보
     * @return 대시보드 페이지
     */
    @PostMapping("registration/regist")
    public String regist(MemberRequest member) {

        int res = 0;

        try {
            res = memberService.insertMember(member);

            if (res == 0){
                log.error("회원가입 실패");
                return "redirect:/member/registration";
            } else {
                return "dashboard/dashboard";
            }

        } catch (Exception e) {
            log.error("회원가입 실패 = {}", e.getMessage());
            return "redirect:/member/registration";
        }
    }
    
    
}
