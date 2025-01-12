package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

/**
 * 유효하지 않은 형식에 대한 예외 처리 클래스.
 */
public class InvalidFormatException extends AbstractionException {
    public InvalidFormatException(ErrorCode code) {
        super(code);
    }
}
