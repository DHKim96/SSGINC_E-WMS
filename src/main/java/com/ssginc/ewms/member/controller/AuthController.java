package com.ssginc.ewms.member.controller;

import com.ssginc.ewms.member.dto.ResponseDto;
import com.ssginc.ewms.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 인증 관련 기능을 수행하는 컨트롤러
 *
 * <ul>관련 기능
 *  <li>이메일 검증</li>
 *  <li>전화번호 검증</li>
 *  <li>아이디 찾기</li>
 *  <li>비밀번호 찾기</li>
 * </ul>
 */
@Slf4j
@RestController // @ResponseBody 생략 가능 data 반환
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 이메일로 인증번호를 전송합니다.
     * @param payload 입력 받은 이메일
     * @return http 응답 객체
     */
    @PostMapping("email")
    public ResponseEntity<ResponseDto<Map<String, String>>> verifyEmail(@RequestBody Map<String, String> payload) {
        String res = authService.verifyEmail(payload.get("email"));
        return ResponseEntity.ok(
                new ResponseDto<>(HttpStatus.OK.value(), "이메일로 인증번호가 전송되었습니다.", Map.of("email", res))
        );
    }

    /**
     * 전화번호로 인증번호를 전송합니다.
     * @param payload 입력 받은 전화번호(000-000/0000-0000)
     * @return http 응답 객체
     */
    @PostMapping("phone")
    public ResponseEntity<ResponseDto<Map<String, String>>> verifyPhone(@RequestBody Map<String, String> payload) {
        String res = authService.verifyPhone(payload.get("phone"));
        return ResponseEntity.ok(
                new ResponseDto<>(HttpStatus.OK.value(), "전화번호로 인증번호가 전송되었습니다.", Map.of("phone", res))
        );
    }

    /**
     * 입력한 이메일로 가입한 아이디를 전송합니다.
     * @param email 입력 받은 이메일
     * @return http 응답 객체
     */
    @GetMapping("id/find")
    public ResponseEntity<ResponseDto<Map<String, String>>> findId(@RequestParam String email) {
        String res = authService.findId(email);
        return ResponseEntity.ok(
                new ResponseDto<>(HttpStatus.OK.value(), "아이디가 이메일로 전송되었습니다.", Map.of("email", res))
        );
    }

    /**
     * 입력받은 아이디로 가입한 이메일과 입력 받은 이메일의 일치 여부 확인 후
     * 임시 비밀번호를 생성하여 해당 이메일로 전송합니다.
     * @param payload 입력 받은 아이디 및 이메일
     * @return HTTP 응답 객체
     */
    @PostMapping("password/find")
    public ResponseEntity<ResponseDto<Map<String, String>>> findPassword (@RequestBody Map<String, String> payload) {
        String res = authService.findPw(payload.get("id"), payload.get("email"));
        return ResponseEntity.ok(
                new ResponseDto<>(HttpStatus.OK.value(), "임시 비밀번호가 이메일로 전송되었습니다.", Map.of("email", res))
        );
    }

    /**
     * 입력 받은 이메일/전화번호로 전송된 인증번호의 일치 여부를 확인합니다.
     * @param payload 이메일/전화번호 및 인증 번호
     * @return HTTP 응답 객체
     */
    @PostMapping("verify")
    public ResponseEntity<ResponseDto<Map<String, String>>> verify(@RequestBody Map<String, String> payload) {
        authService.verifyAuthCode(payload.get("key"), payload.get("value"));
        return ResponseEntity.ok(new ResponseDto<>(HttpStatus.OK.value(), "인증이 완료됐습니다.", null));
    }

}
