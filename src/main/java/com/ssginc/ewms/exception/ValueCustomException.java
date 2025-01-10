package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

/**
 * 입.출력 데이터 처리 관련 예외를 처리하기 위한 커스텀 예외 클래스.
 * <p>
 * 입.출력 데이터 처리 중 발생하는 에러 상황 처리
 */
public class ValueCustomException extends AbstractionException {

    /**
     * 에러 코드와 단일 매개변수를 기반으로 예외를 생성합니다.
     *
     * @param errorCode 에러 코드
     * @param args    메시지 포맷팅에 사용할 단일 매개변수
     */
    protected ValueCustomException(ErrorCode errorCode, String args) {
        super(errorCode, args);
    }

    /**
     * 에러 코드와 원인을 기반으로 예외를 생성합니다.
     *
     * @param errorCode 에러 코드
     * @param cause   예외 원인
     */
    protected ValueCustomException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    /**
     * 에러 코드, 메시지 매개변수 배열, 원인을 기반으로 예외를 생성합니다.
     *
     * @param errorCode 에러 코드
     */
    public ValueCustomException(ErrorCode errorCode) {
        super(errorCode);
    }
}