package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.vo.MemberVO;

public interface MemberService {
    MemberVO selectMemberById(MemberRequest member);

    boolean validatePwd(MemberRequest member, MemberVO loginUser);

    boolean checkEmail(String email);

    boolean authEmail(String email, String authNo);

    boolean checkId(String id);
}
