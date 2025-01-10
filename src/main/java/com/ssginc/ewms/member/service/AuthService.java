package com.ssginc.ewms.member.service;

public interface AuthService {


    boolean findPw(String id);

    boolean findId(String email);

    String verifyEmail(String email);
}
