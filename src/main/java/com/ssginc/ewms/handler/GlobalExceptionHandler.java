package com.ssginc.ewms.handler;

import com.ssginc.ewms.exception.AbstractionException;
import com.ssginc.ewms.util.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 어플리케이션 내에서 발생하는 모든 예외를 전역적으로 처리하는 핸들러
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 모든 커스텀 예외 클래스들을 핸들링하는 메서드입니다.
     * @param e 커스텀 예외 클래스
     * @return HTTP 응답 객체에 포함시킬 객체
     */
    @ExceptionHandler(AbstractionException.class)
    public ResponseEntity<Map<String, Object>> handleAbstractionException(AbstractionException e) {

        // 예외 로그에 스택 트레이스 포함
        log.error("Handled AbstractionException: {}", e.toString(), e);

        Map<String, Object> response = new HashMap<>();
        response.put("code", e.getCode());
        response.put("message", e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response);

    }

    /**
     * 커스텀 예외 클래스들로 잡지 못한 나머지 예외들을 핸들링하는 메서드입니다.
     * @param ex 커스텀 예외 클래스를 제외한 예외 클래스들
     * @return HTTP 응답 객체에 포함시킬 객체
     */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
//
//        log.error("Handled Exception: {}", ex.toString(), ex);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("errorCode", ErrorCode.UNKNOWN_ERROR.getCode());
//        response.put("message", ErrorCode.UNKNOWN_ERROR.getMsg());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {

        log.error("Handled GeneralException: {}", ex.getMessage(), ex);

        // 요청이 JSON 타입인지 확인 => axios 에서 data로 HTML 응답을 받아오는 것을 방지하기 위해 구분
        String acceptHeader = request.getHeader("Accept");
        if (acceptHeader != null && acceptHeader.contains("application/json")) {
            // JSON 응답 반환
            Map<String, Object> errorBody  = new HashMap<>();
            errorBody.put("errorCode", ErrorCode.UNKNOWN_ERROR.getCode());
            errorBody.put("message", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }

        // HTML 페이지 리다이렉트
        String redirectURL = "/error?message=" + URLEncoder.encode("서버로부터 예외가 발생했습니다.", "UTF-8");
        log.info("Redirect URL: {}", redirectURL);
        response.sendRedirect(redirectURL);
        return null;
    }
}
