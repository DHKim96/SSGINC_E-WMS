package com.ssginc.ewms.util;

/**
 * 시스템에서 발생할 수 있는 다양한 에러 코드와 메시지를 정의하는 Enum 클래스
 * <p>
 * 각 에러 코드와 대응되는 메시지 관리
 * 에러 식별을 위한 고유 코드 제공
 */
public enum ErrorCode {

    // 데이터베이스 관련 에러코드
    DATABASE_INSERT_FAILED(500,  "E001", "%s 테이블에 %s 데이터 INSERT 시 오류가 발생했습니다."),
    DATABASE_COMMIT_FAILED(500, "E002", "COMMIT 중 오류가 발생했습니다."),
    DATABASE_ROLLBACK_FAILED(500, "E003", "ROLLBACK 중 오류가 발생했습니다."),
    DATABASE_AUTO_COMMIT_ERROR(500, "E004", "AUTOCOMMIT 설정 중 오류가 발생했습니다."),

    // 인증관련 에러코드
    UNAUTHENTICATED_PROCESS(401, "E201", "인증되지 않은 사용자가 접근하였습니다."),
    FORBIDDEN_RESOURCE(403, "E202", "로그인한 사용자가 접근할 수 없는 경로입니다."),
    MEMBER_NOT_FOUND(404, "E203", "일치하는 사용자를 찾을 수 없습니다."),

    // 리소스 접근 불가 에러코드
    RESOURCE_NOT_FOUND(404, "E204", "해당 리소스로 접근이 불가능합니다."),

    // 입력값 처리 에러코드
    INPUT_VALUE_NOT_FILLED(400, "E205", "필수적으로 입력하지 않은 필드가 존재합니다."),
    EXCEED_LIMIT_VALUE(400, "E206", "필수적으로 입력하지 않은 필드가 존재합니다."),
    DATE_RANGE_ERROR(400, "E207", "기간 범위 설정이 잘못되었습니다."),
    VALUE_RANGE_ERROR(400, "E208", "입력값 범위 설정이 잘못되었습니다."),

    // 데이터 값 처리 에러코드
    NULL_POINT_ERROR(404, "E209", "Null Pointer에 접근하였습니다."),
    NOT_VALID_ERROR(404, "E210", "유효하지 않은 요청값입니다.");

    private final int status;
    private final String code;
    private final String msg;

    /**
     * 에러 코드 및 메시지를 초기화합니다.
     *
     * @param status 상태 코드
     * @param code 에러 코드. 예외 원인 식별화
     * @param msg  에러 메시지
     */
    ErrorCode(int status, String code, String msg) {
        this.status = status;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 상태 코드를 반환합니다.
     *
     * @return 상태 코드
     */
    public int getStatus() {
        return status;
    }

    /**
     * 에러 코드를 반환합니다.
     *
     * @return 에러 코드
     */
    public String getCode() {
        return code;
    }

    /**
     * 에러 메시지를 반환합니다.
     *
     * @return 에러 메시지
     */
    public String getMsg() {
        return msg;
    }
}
