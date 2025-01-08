package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.mapper.MemberMapper;
import com.ssginc.ewms.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String adminEmail;

    @Override
    public MemberVO selectMemberById(MemberRequest member) {
        return memberMapper.selectMemberById(member.getMemberId().trim());
    }

    @Override
    public boolean validatePwd(MemberRequest memberRequest, MemberVO member) {
        return passwordEncoder.matches(member.getMemberPw(), memberRequest.getMemberPw());
    }

    @Override
    public boolean checkEmail(String email) {
        return memberMapper.checkMemberEmail(email) == 1;
    }

    @Override
    public boolean authEmail(String email, String authNo) {
        boolean isSuccess = false;

        SimpleMailMessage msg = new SimpleMailMessage(); // 메시지 생성

        msg.setSubject("[E-WMS] 인증번호가 도착했습니다."); // 제목 생성
        msg.setText("인증번호는 [ " + authNo + " ] 입니다."); // 내용 생성

        msg.setTo(email); // 수신인

        msg.setFrom(adminEmail); // 발신인

        try {
            mailSender.send(msg);
            isSuccess = true;
        } catch (MailException e) {
            isSuccess = false;
        }

        return isSuccess;
    }

    @Override
    public boolean checkId(String id) {
        return memberMapper.checkMemberId(id) == 1;
    }


}
