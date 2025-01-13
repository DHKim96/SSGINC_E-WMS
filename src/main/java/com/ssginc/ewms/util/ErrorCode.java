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
    MEMBER_NOT_FOUND(404, "MEMBER_001", "회원 정보를 찾을 수 없습니다."),
    MEMBER_INSERT_FAILED(500, "MEMBER_002", "회원가입 중 에러가 발생했습니다."),
    INVALID_PASSWORD(401, "MEMBER_003", "비밀번호가 일치하지 않습니다."),


    // 이메일 관련 오류
    EMAIL_SEND_FAILED(500, "EMAIL_001", "이메일 전송에 실패했습니다."),
    INVALID_EMAIL_FORMAT(400, "EMAIL_002", "유효하지 않은 이메일 형식입니다."),
    EMAIL_NOT_FOUND(404, "EMAIL_003", "해당 이메일을 찾을 수 없습니다."),
    EMAIL_CONTENT_EMPTY(404, "E", "이메일 생성에 실패했습니다."),

    // SMS 관련 오류
    SMS_SEND_FAILED(500, "SMS_001", "SMS 전송에 실패했습니다."),
    INVALID_PHONE_FORMAT(400, "SMS_002", "유효하지 않은 전화번호 형식입니다."),

    // 데이터 관련 오류
    DATABASE_ERROR(500, "DB_001", "데이터베이스 처리 중 오류가 발생했습니다."),
    DATA_CONFLICT(409, "DB_002", "데이터가 이미 존재합니다."),

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
    NOT_VALID_ERROR(404, "E210", "유효하지 않은 요청값입니다."),

    // 일반 에러
    UNKNOWN_ERROR(400,"COMMON_001", "알 수 없는 오류가 발생했습니다."),


    // 동현
    MEMBER_UPDATE_FAILED(500, "ME-1", "회원 정보 수정 중 예외가 발생했습니다."),
    ID_NOT_FOUNDED(404, "ME-2", "아이디를 찾을 수 없습니다."),
    PHONE_IS_DUPLICATED(500, "MME-3", "이미 존재하는 핸드폰 번호입니다."),
    EMAIL_IS_DUPLICATED(500, "ME04", "이미 존재하는 이메일입니다."),
    AUTOCODE_NOT_CORRECTED(500, "ME05", "인증번호가 일치하지 않습니다."),
    EMAIL_NOT_CORRECTED(500, "ME06", "입력하신 이메일이 가입 시 입력하신 이메일과 일치하지 않습니다"),
    INVALID_MEMBER_FORMAT(500, "ME07", "유저의 정보 형식이 올바르지 않습니다." );
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
