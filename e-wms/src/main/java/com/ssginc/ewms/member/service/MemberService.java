package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.vo.MemberVO;

public interface MemberService {
    MemberVO selectMemberById(MemberRequest member);
    boolean validatePwd(MemberRequest member, MemberVO loginUser);
}
