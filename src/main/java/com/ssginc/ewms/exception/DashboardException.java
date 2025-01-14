package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

public class DashboardException extends AbstractionException {
    public DashboardException(ErrorCode errorCode) {
        super(errorCode);
    }
}
