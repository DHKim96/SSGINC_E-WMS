package com.ssginc.ewms.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;

/**
 * http 응답 객체에 넣을 상태 코드와 메시지, 응답 데이터를 담은 dto 클래스입니다.
 * @param <T>
 */
@Data
@AllArgsConstructor
public class ResponseDto<T> {
    
    private int status; // HTTP 상태 코드
    private String message; // 메시지
    private T data; // 응답 데이터(Optional)
    
}
