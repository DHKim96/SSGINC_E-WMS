package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.vo.MemberVO;

/**
 * 회원 인증과 관련한 작업을 수행하는 서비스.
 * 
 * <ul>수행 기능
 *  <li>이메일 본인 인증</li>
 *  <li>전화번호 본인 인증</li>
 *  <li>아이디 찾기</li>
 *  <li>비밀번호 찾기</li>
 * </ul>
 */
public interface AuthService {

    /**
     * 임력한 아이디와 이메일을 받아 해당 아이디 가입 시 입력한 이메일이 맞는지 확인 후
     * 임시 비밀번호를 생성하여 이를 입력한 이메일로 전송 후
     * 회원의 비밀번호를 해당 임시비밀번호로 수정합니다.
     * @param id 입력 받은 아이디
     * @param email 입력 받은 이메일
     * @return 전송한 이메일
     */
    String findPw(String id, String email);

    /**
     * 입력 받은 이메일의 가입 여부 확인 후 해당 이메일로 가입한 아이디를 전송합니다.
     * @param email 입력 받은 이메일
     * @return 전송한 이메일
     */
    String findId(String email);

    /**
     * 입력 받은 이메일의 가입 여부 확인 후 존재하지 않을 시 해당 이메일로 6자리 인증 번호를 전송합니다.
     * @param email 입력 받은 이메일
     * @return 전송한 이메일
     */
    String verifyEmail(String email);

    /**
     * 입력 받은 전화번호의 가입 여부를 확인 후 미가입 시 해당 전화번호로 6자리 인증 번호를 전송합니다.
     * @param phone 입력 받은 전화번호
     * @return 전송한 전화번호
     */
    String verifyPhone(String phone);

    /**
     * 입력 받은 이메일 혹은 전화번호와 인증 번호와의 일치 여부를 확인합니다. 
     * @param key 입력 받은 이메일 또는 전화번호
     * @param value 인증 번호
     */
    void verifyAuthCode(String key, String value);

    /**
     * 회원 정보 수정 시 본인 인증 단계에서 회원의 비밀번호와 입력 받은 비밀번호의 일치 여부를 확인합니다.
     *
     * @param loginUser 로그인한 회원 정보
     * @param password  입력 받은 비밀번호
     */
    boolean verifyPassword(MemberVO loginUser, String password);


    boolean verifyPhoneForModify(MemberVO loginUser, String phone);
}
