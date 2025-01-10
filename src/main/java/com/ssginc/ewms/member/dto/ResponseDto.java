package com.ssginc.ewms.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto<T> {
    
    private int status; // HTTP 상태 코드
    private String msg; // 메시지
    private T data; // 응답 데이터(Optional)
    
}
