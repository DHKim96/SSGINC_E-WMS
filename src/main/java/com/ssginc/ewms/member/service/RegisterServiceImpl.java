package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.dto.MemberInsertRequest;
import com.ssginc.ewms.member.mapper.MemberMapper;
import com.ssginc.ewms.util.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * MemberServiceImpl: 회원 관리 서비스 구현체.
 *
 * 주요 역할:
 * - 회원 로그인 및 인증 처리
 * - 이메일 및 전화번호 인증
 * - 중복 체크 기능 (아이디, 이메일, 전화번호)
 * - 회원가입 데이터 처리
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final MemberMapper memberMapper; // 회원 관련 DB 연동 Mapper

    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

    private final MemberValidator memberValidator;

    @Override
    public boolean checkId(String id) {
        memberValidator.validateId(id);
        return memberMapper.checkMemberId(id) == 0;
    }


    @Override
    public int insertMember(MemberInsertRequest member) throws Exception {

        memberValidator.validate(member);

        // 비밀번호 암호화
        member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));

        int res;

        try {
            res = memberMapper.insertMember(member);
        } catch (PersistenceException e) {
            log.error("회원가입 중 에러 발생: {}", e.getMessage());
            throw new SQLException("회원가입 실패", e);
        }

        if (res == 0) {
            throw new Exception("회원가입 실패");
        }

        return res;
    }


}