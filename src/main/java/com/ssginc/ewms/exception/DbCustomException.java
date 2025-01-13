package com.ssginc.ewms.exception;

import com.ssginc.ewms.util.ErrorCode;

/**
 * 데이터베이스 관련 예외를 처리하기 위한 커스텀 예외 클래스.
 * <p>
 * 데이터베이스 작업 중 발생하는 다양한 에러 상황을 처리합니다.
 */
public class DbCustomException extends AbstractionException {
    /**
     * 에러 코드, 단일 매개변수, 원인을 기반으로 예외를 생성합니다.
     *
     * @param errorCode 에러 코드
     * @param args    메시지 포맷팅에 사용할 단일 매개변수
     * @param cause   예외 원인
     */
    public DbCustomException(ErrorCode errorCode, String args, Throwable cause) {
        super(errorCode, new Object[]{args}, cause);
    }

    /**
     * 에러 코드와 테이블 이름 및 컬럼 이름을 기반으로 예외를 생성합니다.
     *
     * @param errorCode    에러 코드
     * @param tableName  테이블 이름
     * @param columnName 컬럼 이름
     */
    public DbCustomException(ErrorCode errorCode, String tableName, String columnName) {
        this(errorCode, tableName, columnName, null);
    }

    /**
     * 에러 코드, 테이블 이름, 컬럼 이름, 원인을 기반으로 예외를 생성합니다.
     *
     * @param errorCode    에러 코드
     * @param tableName  테이블 이름
     * @param columnName 컬럼 이름
     * @param cause      예외 원인
     */
    public DbCustomException(ErrorCode errorCode, String tableName, String columnName, Throwable cause) {
        super(errorCode, new Object[]{tableName, columnName}, cause);
    }
}