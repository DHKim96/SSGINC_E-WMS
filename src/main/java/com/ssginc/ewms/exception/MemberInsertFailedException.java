package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

/**
 * 회원 정보 저장 실패 예외 처리 클래스.
 */
public class MemberInsertFailedException extends AbstractionException {
    public MemberInsertFailedException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
