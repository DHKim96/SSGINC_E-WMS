package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

public class MemberInsertFailedException extends AbstractionException {
    public MemberInsertFailedException(ErrorCode code, Throwable cause) {
        super(code, cause);
    }
}
