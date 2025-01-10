package com.ssginc.ewms.member.service;

import com.ssginc.ewms.exception.InvaildFormatException;
import com.ssginc.ewms.exception.SendFailedException;
import com.ssginc.ewms.member.exception.AuthEmailException;
import com.ssginc.ewms.member.mapper.MemberMapper;
import com.ssginc.ewms.util.RandomGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberMapper memberMapper; // 회원 관련 DB 연동 Mapper

    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화
    private final JavaMailSender mailSender; // 이메일 전송

    private final RandomGenerator randomGenerator; // 인증번호 및 문자열 생성

    private final ConcurrentHashMap<String, String> authNumbers = new ConcurrentHashMap<>();  // 인증번호 저장 (key: 이메일 또는 전화번호, value: 인증번호)

    @Value("${spring.mail.username}")
    private String adminEmail; // 발신 이메일 주소

    /**
     * 이메일 내용을 생헝하고 이메일로 전송하는 메서드
     * @param email 수신 이메일 주소
     * @param subject 이메일 제목
     * @param contents 이메일 내용
     * @return 전송 성공 여부
     */
    private boolean sendMail(String email, String subject, String contents) {
        boolean isSuccess = false;

        SimpleMailMessage msg = new SimpleMailMessage(); // 메시지 생성

        msg.setSubject(subject); // 제목 생성
        msg.setText(contents); // 내용 생성
        msg.setTo(email); // 수신인
        msg.setFrom(adminEmail); // 발신인

        if (email == null ){
            throw new InvaildFormatException(ErrorCode.E);
        }

        if (subject == null) {
            throw new AuthEmailException("이메일 생성을 위한 제목이 비어있습니다.");
        }

        if (contents == null) {
            throw new AuthEmailException("이메일 생성을 위한 내용이 비어있습니다.");
        }

        try {
            mailSender.send(msg);
            isSuccess = true;
            log.info("이메일이 {}에게 전송되었습니다.", email);
        } catch (MailException e) {
            throw new AuthEmailException("이메일 생성 중 예외가 발생했습니다.", e);
        }

        return isSuccess;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean findPw(String id) {

        String generatedPw = randomGenerator.generateRandomStr();

        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("generatedPw", passwordEncoder.encode(generatedPw)); // 암호화한 임시 비밀번호로 수정

        int res = memberMapper.updateMemberPw(map);

        if (res == 0) {
            throw new AuthEmailException("사용자 정보 수정에 실패했습니다.");
        }

        String email = memberMapper.selectMemberEmailById(id);

        if (email == null) {
            throw new AuthEmailException(id + "에 일치하는 이메일이 존재하지 않습니다.");
        }

        String subject = "[EWMS] 임시 비밀번호 발급 서비스입니다.";

        String contents = "회원님의 임시 비밀번호는 [ " + generatedPw + " ] 입니다.";

        return sendMail(email, subject, contents);
    }

    @Override
    public boolean findId(String email) {

        String memberId = memberMapper.selectMemberIdByEmail(email);

        if (memberId == null) {
            throw new AuthEmailException(email + "에 일치하는 아이디가 존재하지 않습니다.");
        }

        String subject = "[EWMS] 아이디 찾기 서비스입니다.";

        String contents = "회원님의 아이디는 [ " + memberId + " ] 입니다.";

        return sendMail(email, subject, contents);
    }

    @Override
    public String verifyEmail(String email) {



        return "";
    }


}
