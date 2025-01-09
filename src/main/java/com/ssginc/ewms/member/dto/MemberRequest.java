package com.ssginc.ewms.member.dto;

import lombok.Builder;
import lombok.Data;

/**
 * 회원 요청 데이터를 캡슐화하는 DTO 클래스입니다.
 *
 * <p>주요 역할:</p>
 * <ul>
 *     <li>회원 가입, 수정 시 클라이언트로부터 전달받은 데이터를 보관</li>
 *     <li>서비스 계층으로 데이터를 전달하기 위한 객체</li>
 * </ul>
 */
@Data
@Builder
public class MemberRequest {

    /**
     * 회원 아이디
     */
    private String memberId;

    /**
     * 회원 비밀번호
     */
    private String memberPw;

    /**
     * 회원 이름
     */
    private String memberName;

    /**
     * 회원 이메일
     */
    private String memberEmail;

    /**
     * 회원 전화번호
     */
    private String memberPhone;

    /**
     * 우편번호
     */
    private String memberPost;

    /**
     * 상세 주소
     */
    private String memberAddr;

    /**
     * 회원 생년월일
     */
    private String memberBirth;

//    /**
//     * 회원이 소속된 창고 ID (향후 확장을 위해 주석 처리)
//     */
//    private int warehouseId;
}
