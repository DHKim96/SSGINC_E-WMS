package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.dto.MemberInsertRequest;
import com.ssginc.ewms.member.vo.MemberVO;

public interface LoginService {
    /**
     * 입력받은 비밀번호와 DB에 저장된 비밀번호를 검증합니다.
     *
     * @param member 요청된 회원 데이터
     * @param loginUser DB에서 조회된 회원 정보
     * @return 비밀번호가 일치하면 true, 그렇지 않으면 false
     */
    boolean validatePwd(MemberInsertRequest member, MemberVO loginUser);

    MemberVO selectMemberById(MemberInsertRequest member);

}
