package com.ssginc.ewms.member.controller;

import com.ssginc.ewms.member.dto.MemberInsertRequest;
import com.ssginc.ewms.member.dto.MemberUpdateRequest;
import com.ssginc.ewms.member.dto.ResponseDto;
import com.ssginc.ewms.member.service.ModifyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller
@RequestMapping("modify")
@RequiredArgsConstructor
public class ModifyController {

    private final ModifyService modifyService;

    @GetMapping("verify")
    public String verify() {
        return "member/verify";
    }

    @GetMapping("")
    public String modifyMember(HttpSession session) {
//        Long authExpiry = (Long) session.getAttribute("authExpiration");
//
//        if (authExpiry == null || System.currentTimeMillis() > authExpiry) {
//            session.invalidate();
//            return "redirect:/modify/verify"; // 인증 만료 시 인증 페이지로 이동
//        }

        return "member/modify";
    }

    @PostMapping("member")
    @ResponseBody
    public ResponseEntity<ResponseDto<Map<String, Integer>>> modifyMember(@RequestBody MemberUpdateRequest member) {

        int result = modifyService.updateMember(member);

        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "회원정보 수정에 성공했습니다.", Map.of("result", result)));
    }
}
