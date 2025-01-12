package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

/**
 * 회원 정보를 찾지 못할 경우 예외 처리 클래스.
 */
public class MemberNotFoundException extends AbstractionException {
    public MemberNotFoundException(ErrorCode code) {
        super(code);
    }
}
