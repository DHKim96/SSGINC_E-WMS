package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

public class InvaildFormatException extends AbstractionException {
    public InvaildFormatException(ErrorCode code) {
        super(code);
    }
}
