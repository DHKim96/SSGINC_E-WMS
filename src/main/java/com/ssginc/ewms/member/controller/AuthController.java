package com.ssginc.ewms.member.controller;

import com.ssginc.ewms.member.dto.ResponseDto;
import com.ssginc.ewms.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class AuthController {

    private final AuthService authService;


    @PostMapping("auth/email/verify")
    public ResponseDto<Void> verifyEmail(@RequestParam("email") String email) {
        authService.verifyEmail(email);
        return new ResponseDto<Void>(HttpStatus.OK.value(), "이메일로 인증번호가 전송되었습니다.", null);
    }

    @PostMapping("auth/id/find")
    public ResponseDto<Void> findId(String email) {
        authService.findId(email);
        return new ResponseDto<Void>(HttpStatus.OK.value(), "임시 비밀번호가 이메일로 전송되었습니다.", null);
    }

    @PostMapping("auth/password/find")
    public ResponseEntity<Map<String, String>> findPassword(String id) {

        if (authService.findPw(id)) {
            Map<String, String> response = Map.of(
                    "status", "success",
                    "message", "임시 비밀번호가 이메일로 전송되었습니다."
            );
            return ResponseEntity.ok(response);
        }

        Map<String, String> errorResponse = Map.of(
                "status", "error",
                "message", "전송에 실패했습니다."
        );
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }



}
