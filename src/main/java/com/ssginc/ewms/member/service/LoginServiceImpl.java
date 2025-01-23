package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.dto.MemberInsertRequest;
import com.ssginc.ewms.member.mapper.MemberMapper;
import com.ssginc.ewms.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화
    private final MemberMapper memberMapper; // 회원 관련 DB 연동 Mapper

    @Override
    public boolean validatePwd(MemberInsertRequest member, MemberVO loginUser) {
        System.out.println("@@@@@@@@@@@@@@@ admin = " + passwordEncoder.encode("admin") );
        return passwordEncoder.matches(member.getMemberPw(), loginUser.getMemberPw());
    }


    @Override
    public MemberVO selectMemberById(MemberInsertRequest member) {
        return memberMapper.selectMemberById(member.getMemberId().trim());
    }

}
