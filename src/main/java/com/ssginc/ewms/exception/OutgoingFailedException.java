package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

public class OutgoingFailedException extends AbstractionException {
    public OutgoingFailedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public OutgoingFailedException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
