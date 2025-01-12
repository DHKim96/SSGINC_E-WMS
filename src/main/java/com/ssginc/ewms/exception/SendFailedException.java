package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

/**
 * SMS 발송, EMAIL 전송 실패 시 예외처리 클래스.
 */
public class SendFailedException extends AbstractionException {
    public SendFailedException(ErrorCode code) {
        super(code);
    }
    public SendFailedException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
