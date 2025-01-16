package com.ssginc.ewms.member.service;

import com.ssginc.ewms.exception.MemberNotFoundException;
import com.ssginc.ewms.member.dto.MemberUpdateRequest;
import com.ssginc.ewms.member.mapper.MemberMapper;
import com.ssginc.ewms.util.ErrorCode;
import com.ssginc.ewms.util.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModifyServiceImpl implements ModifyService{

    private final MemberMapper memberMapper;

    private final PasswordEncoder passwordEncoder;

    private final MemberValidator memberValidator;

    @Override
    public int updateMember(MemberUpdateRequest member) {

        if (member == null) {
            throw new MemberNotFoundException(ErrorCode.NULL_POINT_ERROR);
        }

        if (member.getMemberPw() != null){
            String pw = member.getMemberPw();
            memberValidator.validatePassword(pw);
            member.setMemberPw(passwordEncoder.encode(pw));
        }

        if (member.getMemberEmail() != null){
            memberValidator.validateEmail(member.getMemberEmail());
        }

        if (member.getMemberPhone() != null){
            memberValidator.validatePhone(member.getMemberPhone());
        }

        return memberMapper.updateMember(member);
    }
}
