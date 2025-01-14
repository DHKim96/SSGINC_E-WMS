package com.ssginc.ewms.smtp.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmtpService {

    private final JavaMailSender javaMailSender;

    public void sendRequest(int type, String email, String name, String fileName) throws MessagingException {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            // 콜백 메서드 구현
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                // MimeMessageHelper 생성
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                // 받는 사람 이메일
                helper.setTo(email);

                if (type == 0) {
                    // 이메일 제목
                    helper.setSubject("입고요청");
                    // 메일 내용
                    helper.setText("다음과 같이 입고요청합니다.");
                } else {
                    // 이메일 제목
                    helper.setSubject("출고요청");
                    // 메일 내용
                    helper.setText("다음과 같이 출고요청합니다.");
                }

                // 첨부 파일 추가
                ClassPathResource fileResource = new ClassPathResource(name);
                helper.addAttachment(fileName, fileResource);
            }
        };

        try {
            // 메일 전송
            this.javaMailSender.send(preparator);
        } catch (MailException e) {
            throw e;
        }
    }
}