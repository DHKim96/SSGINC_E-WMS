package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

public class ApiKakaoNaviException extends AbstractionException {
  public ApiKakaoNaviException(ErrorCode errorCode) {
    super(errorCode);
  }
    public ApiKakaoNaviException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
