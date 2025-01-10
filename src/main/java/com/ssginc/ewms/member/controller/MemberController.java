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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
     * @param redirectAttributes 일회성 메세지 용도
     * @return 로그인 결과에 따라 주소 담을 ModelAndView
     */
    @PostMapping("/login")
    public String login(MemberRequest member,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {

        MemberVO loginUser = memberService.selectMemberById(member);

        if (loginUser == null) {
            // RedirectAttributes 는 현재 요청에서만 유효
            redirectAttributes.addFlashAttribute("alertMsg", "아이디가 일치하지 않습니다.");
            return "redirect:/"; // 리다이렉트하면서 메시지 전달
        } else if (!memberService.validatePwd(member, loginUser)) {
            redirectAttributes.addFlashAttribute("alertMsg", "비밀번호가 일치하지 않습니다.");
            return "redirect:/";
        }

        // 로그인 성공
        session.setAttribute("loginUser", loginUser);
        return "dashboard/dashboard"; // 성공 시 대시보드로 이동
    }



<<<<<<< Updated upstream
    @GetMapping("login/findPw")
    @ResponseBody
    public boolean findPw(String id){
        return memberService.findPw(id);
    }
    
=======
    @GetMapping("login/findPw/checkEmail")
    @ResponseBody
    public boolean checkEmailForFindPw(String id, String email) {
        return email.equals(memberService.selectMemberEmailById(id));
    }

>>>>>>> Stashed changes
    
    // =========================================== 회원가입 ========================================================
    
    /**
     * 회원가입 버튼 클릭 시 현재 날짜 이후로 생년월일을 선택하지 못하게 하고자
     * 현재 날짜를 회원가입 페이지에 동적으로 매핑해줍니다.
     * @param model Model
     * @return 회원가입 페이지 주소
     */
    @GetMapping("registration")
    public String registration(Model model) {

        LocalDate today = LocalDate.now();

        model.addAttribute("today", today.toString());
        model.addAttribute("pastDate", today.minusYears(150));

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
     * 클라이언트로부터 입력받은 이메일 주소로 난수를 생성해서 전송합니다.
     * @param email 회원의 이메일 주소
     * @return 전송 성공 여부
     */
    @GetMapping("registration/authEmail")
    @ResponseBody
    public boolean authEmail(String email) {
        return memberService.authEmail(email);
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
     * @param phone 회원 전화번호, 6자리 난수
     * @return 중복 여부
     */
    @GetMapping("registration/authPhone")
    @ResponseBody
    public boolean authPhone(String phone) {
        return memberService.authPhone(phone);
    }

    @PostMapping("registration/verifyAuthNo")
    @ResponseBody
    public boolean verifyAuthNo(String key, String authNo) {
        return memberService.verifyAuthNo(key, authNo);
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
