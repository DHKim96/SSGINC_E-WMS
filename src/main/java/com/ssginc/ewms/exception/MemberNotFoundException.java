package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

public class MemberNotFoundException extends AbstractionException {
    public MemberNotFoundException(ErrorCode code) {
        super(code);
    }
}
