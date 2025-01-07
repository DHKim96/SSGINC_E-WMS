package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.mapper.MemberMapper;
import com.ssginc.ewms.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberVO selectMemberById(MemberRequest member) {
        return memberMapper.selectMemberById(member.getMemberId().trim());
    }

    @Override
    public boolean validatePwd(MemberRequest memberRequest, MemberVO member) {
        return passwordEncoder.matches(member.getMemberPw(), memberRequest.getMemberPw());
    }


}
