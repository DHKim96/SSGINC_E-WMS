package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.vo.MemberVO;

/**
 * 회원 관리 기능을 관장하는 서비스입니다.
 *
 * <p>주요 기능:</p>
 * <ul>
 *     <li>회원 조회</li>
 *     <li>비밀번호 검증</li>
 *     <li>아이디, 이메일, 전화번호 중복 체크</li>
 *     <li>이메일 및 SMS 인증</li>
 *     <li>회원 정보 저장</li>
 * </ul>
 */
public interface MemberService {

    /**
     * 주어진 회원 아이디를 통해 회원 정보를 조회합니다.
     *
     * @param member 회원 아이디 정보를 담은 요청 객체
     * @return 회원 정보 VO 객체
     */
    MemberVO selectMemberById(MemberRequest member);

    /**
     * 입력받은 비밀번호와 DB에 저장된 비밀번호를 검증합니다.
     *
     * @param member 요청된 회원 데이터
     * @param loginUser DB에서 조회된 회원 정보
     * @return 비밀번호가 일치하면 true, 그렇지 않으면 false
     */
    boolean validatePwd(MemberRequest member, MemberVO loginUser);

    /**
     * 이메일 중복 여부를 확인합니다.
     *
     * @param email 이메일 주소
     * @return 중복되지 않으면 true, 중복되면 false
     */
    boolean checkEmail(String email);

    /**
     * 입력받은 이메일 주소로 인증번호를 전송합니다.
     *
     * @param email 이메일 주소
     * @param authNo 인증번호
     * @return 이메일 전송 성공 여부
     */
    boolean authEmail(String email, String authNo);

    /**
     * 회원 아이디 중복 여부를 확인합니다.
     *
     * @param id 회원 아이디
     * @return 중복되지 않으면 true, 중복되면 false
     */
    boolean checkId(String id);

    /**
     * 전화번호 중복 여부를 확인합니다.
     *
     * @param phone 회원 전화번호
     * @return 중복되지 않으면 true, 중복되면 false
     */
    boolean checkPhone(String phone);

    /**
     * 입력받은 전화번호로 인증번호를 전송합니다.
     *
     * @param phone 회원 전화번호
     * @param authNo 인증번호
     * @return 인증번호 전송 성공 여부
     */
    boolean authPhone(String phone, String authNo);

    /**
     * 회원 정보를 데이터베이스에 저장합니다.
     *
     * @param member 회원 요청 데이터
     * @return 저장된 행의 개수 (정상적으로 저장되면 1)
     * @throws Exception 저장 중 에러 발생 시 예외 발생
     */
    int insertMember(MemberRequest member) throws Exception;

    boolean findId(String email);

    boolean findPw(String id);

    String selectMemberEmailById(String id);
}
