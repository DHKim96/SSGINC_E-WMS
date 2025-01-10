package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

public class SendFailedException extends AbstractionException {
    public SendFailedException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
