package com.ssginc.ewms.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberUpdateRequest {

    /**
     * 회원 아이디
     */
    private String memberId;

    /**
     * 회원 비밀번호
     */
    private String memberPw;

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

}
