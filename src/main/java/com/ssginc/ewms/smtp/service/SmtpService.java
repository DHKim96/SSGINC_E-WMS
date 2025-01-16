package com.ssginc.ewms.smtp.service;

import jakarta.mail.MessagingException;

public interface SmtpService {
    void sendRequest(int type, String email, String name, String fileName) throws MessagingException;
}
