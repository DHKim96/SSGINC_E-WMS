package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

public class ApiWeatherException extends AbstractionException {
    public ApiWeatherException(ErrorCode errorCode) {
        super(errorCode);
    }
  public ApiWeatherException(ErrorCode errorCode, Throwable cause) {
    super(errorCode, cause);
  }
}
