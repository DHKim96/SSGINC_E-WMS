package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.vo.MemberVO;

public interface LoginService {
    /**
     * 입력받은 비밀번호와 DB에 저장된 비밀번호를 검증합니다.
     *
     * @param member 요청된 회원 데이터
     * @param loginUser DB에서 조회된 회원 정보
     * @return 비밀번호가 일치하면 true, 그렇지 않으면 false
     */
    boolean validatePwd(MemberRequest member, MemberVO loginUser);

    /**
     * 입력받은 이메일에 해당 회원의 아이디를 전송합니다.
     * @param email 요청받은 회원 이메일
     * @return 성공 여부
     */
    boolean findId(String email);

    /**
     * 입력받은 아이디로 가입한 회원의 이메일에 임시 비밀번호를 전송합니다.
     * @param id 요청받은 회원 아이디
     * @return 전송 성공 여부
     */
    boolean findPw(String id);
}
