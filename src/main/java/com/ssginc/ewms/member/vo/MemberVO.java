package com.ssginc.ewms.member.vo;

import lombok.*;

/**
 * EWMS 사용자 정보를 담은 VO 클래스.
 *
 * 주요 필드 :
 * <ul>
 *     <li>memberNo - 유저 식별자</li>
 *     <li>memberId - 유저 아이디</li>
 *     <li>memberPw - 유저 비밀번호</li>
 *     <li>memberName - 유저명</li>
 *     <li>memberEmail - 유저 이메일</li>
 *     <li>memberPhone - 유저 전화번호</li>
 *     <li>memberPost - 유저 우편번호</li>
 *     <li>memberAddr - 유저 주소</li>
 *     <li>memberBirth - 유저 생년월일</li>
 *     <li>memberDate - 유저 가입일</li>
 *     <li>memberIsActive - 유저 상태</li>
 *     <li>memberRole - 유저 권한</li>
 *     <li>warehouseId - 관할 창고 번호</li>
 * </ul>
 */
@Data
public class MemberVO {
    private int memberNo;
    private String memberId;
    private String memberPw;
    private String memberName;
    private String memberEmail;
    private String memberPhone;
    private String memberPost;
    private String memberAddr;
    private String memberBirth;
    private String memberDate;
    private String memberIsActive;
    private int memberRole;
    private int warehouseId;
}
