package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.mapper.MemberMapper;
import com.ssginc.ewms.util.RandomGenerator;
import com.ssginc.ewms.member.vo.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
<<<<<<< Updated upstream
=======
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
>>>>>>> Stashed changes

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
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper; // 회원 관련 DB 연동 Mapper

    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화
    private final JavaMailSender mailSender; // 이메일 전송

    private final RandomGenerator randomGenerator; // 인증번호 및 문자열 생성

    private final ConcurrentHashMap<String, String> authNumbers = new ConcurrentHashMap<>();  // 인증번호 저장 (key: 이메일 또는 전화번호, value: 인증번호)

    @Value("${spring.mail.username}")
    private String adminEmail; // 발신 이메일 주소

    @Value("${coolsms.client.id}")
    private String smsApiKey; // CoolSMS API 키

    @Value("${coolsms.client.secret}")
    private String smsApiSecret; // CoolSMS API 비밀 키

    @Value("${coolsms.client.phone}")
    private String smsApiPhone; // 발신 전화번호


    @Override
    public MemberVO selectMemberById(MemberRequest member) {
        return memberMapper.selectMemberById(member.getMemberId().trim());
    }


    @Override
    public boolean validatePwd(MemberRequest memberRequest, MemberVO member) {
        return passwordEncoder.matches(memberRequest.getMemberPw(), member.getMemberPw());
    }


    @Override
    public boolean checkEmail(String email) {
        return memberMapper.checkMemberEmail(email) == 0;
    }

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

        try {
            mailSender.send(msg);
            isSuccess = true;
        } catch (MailException e) {
            log.error("이메일 전송 실패: {}", e.getMessage());
            isSuccess = false;
        }

        return isSuccess;
    }

    @Override
    public boolean authEmail(String email) {
        String authNo = randomGenerator.generateRandomNum();

        String subject = "[E-WMS] 인증번호가 도착했습니다.";
        String contents = "인증번호는 [ " + authNo + " ] 입니다.";

        boolean isSuccess = sendMail(email, subject, contents);

        authNumbers.put(email, authNo); // 인증번호 저장

        return isSuccess;
    }
    


    @Override
    public boolean checkId(String id) {
        return memberMapper.checkMemberId(id) == 0;
    }


    @Override
    public boolean checkPhone(String phone) {
        return memberMapper.checkMemberPhone(phone) == 0;
    }


    @Override
    public boolean authPhone(String phone) {
        String authNo = randomGenerator.generateRandomNum();

        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(smsApiKey, smsApiSecret, "https://api.coolsms.co.kr");

        Message message = new Message();
        phone = phone.replace("-", ""); // 하이픈 제거
        message.setFrom(smsApiPhone); // 발신 번호
        message.setTo(phone); // 수신 번호
        message.setText("EWMS 인증번호는 [" + authNo + "] 입니다.");

        boolean isSuccess = false;

        try {
            messageService.send(message);
            isSuccess = true;
            Balance balance = messageService.getBalance();
            log.info("메시지 전송 후 잔액 = {}", balance);
        } catch (NurigoMessageNotReceivedException e) {
            log.error("발송 실패한 메시지 목록: {}", e.getFailedMessageList());
            throw new RuntimeException(e);
        } catch (NurigoEmptyResponseException | NurigoUnknownException e) {
            log.error("SMS 전송 실패: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        authNumbers.put(phone, authNo);

        return isSuccess;
    }

    @Override
    public int insertMember(MemberRequest member) throws Exception {
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

    @Override
    public boolean findId(String email) {

        String memberId = memberMapper.selectMemberIdByEmail(email);

        String subject = "[EWMS] 아이디 찾기 서비스입니다.";

        String contents = "회원님의 아이디는 [ " + memberId + " ] 입니다.";

        return sendMail(email, subject, contents);
    }

    @Override
    public boolean findPw(String id) {

        String generatedPw = randomGenerator.generateRandomStr();

<<<<<<< Updated upstream
        int res = memberMapper.updateMemberPw(generatedPw, id);
=======
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("generatedPw", passwordEncoder.encode(generatedPw)); // 암호화한 임시 비밀번호로 수정

        int res = memberMapper.updateMemberPw(map);
>>>>>>> Stashed changes

        if (res == 0) {
            return false;
        }

        return false;
    }

<<<<<<< Updated upstream
    /**
     * 숫자, 영문 대문자, 영문 소문자 조합의 랜덤한 문자열을 생성합니다.
     * @param length 문자열 자릿수
     * @return 랜덤 문자열
     */
    private String generateRandomStr(int length) {
        SecureRandom random = new SecureRandom();

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            sb.append((char)random.nextInt(126-48) + 48);
        }

        return sb.toString();
=======

    @Override
    public boolean verifyAuthNo(String key, String authNo) {

        String storedAuthNo = authNumbers.get(key);

        if (authNo.equals(storedAuthNo)) {
            authNumbers.remove(key);
            return true;
        }

        return false;
>>>>>>> Stashed changes
    }


}