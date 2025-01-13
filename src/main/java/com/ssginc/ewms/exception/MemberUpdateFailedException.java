package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

/**
 * 회원 정보 수정 실패 시 예외 처리 클래스
 */
public class MemberUpdateFailedException extends AbstractionException {
  public MemberUpdateFailedException(ErrorCode code) {
    super(code);
  }
  public MemberUpdateFailedException(ErrorCode code, Throwable cause) {
    super(code, cause);
  }
}
