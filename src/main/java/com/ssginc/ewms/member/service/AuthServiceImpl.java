package com.ssginc.ewms.member.service;

import com.ssginc.ewms.exception.InvalidFormatException;
import com.ssginc.ewms.exception.MemberNotFoundException;
import com.ssginc.ewms.exception.MemberUpdateFailedException;
import com.ssginc.ewms.exception.SendFailedException;
import com.ssginc.ewms.member.mapper.MemberMapper;
import com.ssginc.ewms.member.vo.MemberVO;
import com.ssginc.ewms.util.ErrorCode;
import com.ssginc.ewms.util.validator.MemberValidator;
import com.ssginc.ewms.util.RandomGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Balance;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MemberMapper memberMapper; // 회원 관련 DB 연동 Mapper

    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화
    private final JavaMailSender mailSender; // 이메일 전송

    private final RandomGenerator randomGenerator; // 인증번호 및 문자열 생성

    private final MemberValidator memberValidator; // 유저 유효성 검사

    @Value("${spring.mail.username}")
    private String adminEmail; // 발신 이메일 주소

    @Value("${coolsms.client.id}")
    private String smsApiKey; // CoolSMS API 키

    @Value("${coolsms.client.secret}")
    private String smsApiSecret; // CoolSMS API 비밀 키

    @Value("${coolsms.client.phone}")
    private String smsApiPhone; // 발신 전화번호

    /**
     * 인증 번호의 키와 인증 번호의 유효 기간을 담은 내부 클래스입니다.
     */
    static class AuthData implements Comparable<AuthData> {
        private final String key;
        private final long expirationTime;

        AuthData(String key, long expirationTime) {
            this.key = key;
            this.expirationTime = expirationTime;
        }

        String getKey() {
            return key;
        }

        long getExpirationTime() {
            return expirationTime;
        }


        @Override
        public int compareTo(AuthData other) {
            return Long.compare(this.expirationTime, other.expirationTime);
        }
    }

    private final ConcurrentHashMap<String, String> authNumbers = new ConcurrentHashMap<>();  // 인증번호 저장 (key: 이메일 또는 전화번호, value: 인증번호)
   
    private final PriorityQueue<AuthData> expirationQueue = new PriorityQueue<>(); // 인증번호 만료일 기준으로 오름차순하는 우선순위 큐


    /**
     * 이메일 내용을 생헝하고 이메일로 전송하는 메서드
     * @param email 수신 이메일 주소
     * @param subject 이메일 제목
     * @param contents 이메일 내용
     * @return 전송한 이메일
     */
    private String sendMail(String email, String subject, String contents) {
        if (email == null || subject == null || contents == null) {
            throw new InvalidFormatException(ErrorCode.EMAIL_CONTENT_EMPTY);
        }

        SimpleMailMessage msg = new SimpleMailMessage(); // 메시지 생성

        msg.setSubject(subject); // 제목 생성
        msg.setText(contents); // 내용 생성
        msg.setTo(email); // 수신인
        msg.setFrom(adminEmail); // 발신인


        try {
            mailSender.send(msg);
            log.info("이메일이 {}에게 전송되었습니다.", email);
        } catch (MailException e) {
            throw new SendFailedException(ErrorCode.EMAIL_SEND_FAILED, e);
        }

        return email;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String findPw(String id, String email) {

        memberValidator.validateId(id);

        if ( memberMapper.checkMemberId(id) == 0) {
            throw new MemberNotFoundException(ErrorCode.ID_NOT_FOUNDED);
        }

        memberValidator.validateEmail(email);

        String resEmail = memberMapper.selectMemberEmailById(id);

        if (resEmail == null) {
            log.error("회원 ID {}에 대한 이메일이 존재하지 않습니다.", id);
            throw new MemberNotFoundException(ErrorCode.EMAIL_NOT_FOUND);
        }

        if (!email.equals(resEmail)){
            throw new SendFailedException(ErrorCode.EMAIL_NOT_CORRECTED);
        }

        String generatedPw = randomGenerator.generateRandomStr();

        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("generatedPw", passwordEncoder.encode(generatedPw)); // 암호화된 비밀번호 저장

        try {
            int res = memberMapper.updateMemberPw(map);
            if (res == 0) {
                throw new MemberUpdateFailedException(ErrorCode.MEMBER_UPDATE_FAILED);
            }
        } catch (Exception e) {
            log.error("비밀번호 업데이트 중 예외 발생: {}", e.getMessage(), e);
            throw new MemberUpdateFailedException(ErrorCode.MEMBER_UPDATE_FAILED, e);
        }


        String subject = "[EWMS] 임시 비밀번호 발급 서비스입니다.";
        String contents = "회원님의 임시 비밀번호는 [ " + generatedPw + " ] 입니다.";

        return sendMail(email, subject, contents);
    }

    @Override
    public String findId(String email) {
        memberValidator.validateEmail(email);

        String memberId = memberMapper.selectMemberIdByEmail(email);

        if (memberId == null) {
            throw new MemberNotFoundException(ErrorCode.ID_NOT_FOUNDED);
        }

        String subject = "[EWMS] 아이디 찾기 서비스입니다.";

        String contents = "회원님의 아이디는 [ " + memberId + " ] 입니다.";

        return sendMail(email, subject, contents);
    }

    @Override
    public String verifyEmail(String email) {

        memberValidator.validateEmail(email);

        if (!checkEmail(email)) {
            throw new MemberNotFoundException(ErrorCode.EMAIL_IS_DUPLICATED);
        }

        String authCode = randomGenerator.generateRandomNum(); // 인증번호 생성
        authNumbers.put(email, authCode); // 인증번호 저장

        String subject = "[EWMS] 이메일 인증번호입니다.";
        String contents = "인증번호는 [ " + authCode + " ] 입니다.";

        sendMail(email, subject, contents);
        return email;
    }

    @Override
    public String verifyPhone(String phone) {

        memberValidator.validatePhone(phone);

        if (!checkPhone(phone)) {
            throw new MemberNotFoundException(ErrorCode.PHONE_IS_DUPLICATED);
        }

        String authCode = generatedSecret(phone); // 인증번호 저장

        String text = "[EWMS] 인증번호는 [" + authCode + "] 입니다.";

        return sendSms(phone, text);
    }

    /**
     * 유효기간이 지난 인증번호를 삭제하고 인증번호를 생성합니다.
     * @param key 이메일 또는 전화번호
     * @return 생성된 인증번호
     */
    private String generatedSecret(String key) {

        removeExpiredSecret();

        String authCode = randomGenerator.generateRandomNum();

        authNumbers.put(key, authCode);

        expirationQueue.offer(new AuthData(authCode, System.currentTimeMillis() + 180000)); // 제한 시간 3분

        return authCode;
    }

    /**
     * 유효기간이 지난 인증번호를 삭제합니다.
     */
    private void removeExpiredSecret(){

        long currentTime = System.currentTimeMillis();

        while(!expirationQueue.isEmpty() && expirationQueue.peek().getExpirationTime() <= currentTime) {
            AuthData authData = expirationQueue.poll();
            authNumbers.remove(authData.getKey());
        }
    }

    @Override
    public void verifyAuthCode(String key, String value) {
        String storedAuthNo = authNumbers.get(key);

        if (!value.equals(storedAuthNo)) {
            throw new MemberNotFoundException(ErrorCode.AUTOCODE_NOT_CORRECTED);
        }

        authNumbers.remove(key);
    }

    @Override
    public boolean verifyPassword(MemberVO loginUser, String password) {
        if (loginUser == null) {
            throw new MemberNotFoundException(ErrorCode.NULL_POINT_ERROR);
        }

        String userPwd = memberMapper.selectMemberPwByMemberNo(loginUser.getMemberNo());

        if (!passwordEncoder.matches(password, userPwd)){
            throw new MemberNotFoundException(ErrorCode.INVALID_PASSWORD);
        }

        return true;
    }

    @Override
    public boolean verifyPhoneForModify(MemberVO loginUser, String phone) {
        if (loginUser == null) {
            throw new MemberNotFoundException(ErrorCode.NULL_POINT_ERROR);
        }

        memberValidator.validatePhone(phone);

        String userPhone = memberMapper.selectMemberPhoneByMemberNo(loginUser.getMemberNo());

        if (!userPhone.equals(phone)) {
            throw new MemberNotFoundException(ErrorCode.PHONE_NOT_CORRECTED);
        }

        String authCode = generatedSecret(phone); // 인증번호 저장

        String text = "[EWMS] 인증번호는 [" + authCode + "] 입니다.";

        sendSms(phone, text);

        return true;
    }

    /**
     * 이메일 가입 여부를 체크합니다.
     * @param email 입력 받은 이메일
     * @return 가입 여부
     */
    private boolean checkEmail(String email) {
        return memberMapper.checkMemberEmail(email) == 0;
    }

    /**
     * 전화번호의 가입 여부를 체크합니다.
     * @param phone 입력 받은 전화번호
     * @return 가입 여부
     */
    private boolean checkPhone(String phone) {
        return memberMapper.checkMemberPhone(phone) == 0;
    }


    /**
     * sms 문자 메시지를 전송합니다.
     * @param phone 입력 받은 전화번호
     * @param text 생성된 텍스트
     * @return 전송한 전화번호
     */
    private String sendSms(String phone, String text) {

        DefaultMessageService messageService = NurigoApp.INSTANCE.initialize(smsApiKey, smsApiSecret, "https://api.coolsms.co.kr");

        Message message = new Message();
        phone = phone.replace("-", ""); // 하이픈 제거
        message.setFrom(smsApiPhone); // 발신 번호
        message.setTo(phone); // 수신 번호
        message.setText(text);

        try {
            messageService.send(message);
            Balance balance = messageService.getBalance();
            log.info("SMS 비밀번호 찾기 서비스 전송 번호 : {}", phone);
            log.info("메시지 전송 후 잔액 = {}", balance);
        } catch (NurigoMessageNotReceivedException e) {
            log.error("발송 실패한 메시지 목록: {}", e.getFailedMessageList());
            throw new SendFailedException(ErrorCode.SMS_SEND_FAILED, e);
        } catch (NurigoEmptyResponseException | NurigoUnknownException e) {
            log.error("SMS 전송 실패: {}", e.getMessage());
            throw new SendFailedException(ErrorCode.SMS_SEND_FAILED, e);
        }

        return phone;
    }
}
