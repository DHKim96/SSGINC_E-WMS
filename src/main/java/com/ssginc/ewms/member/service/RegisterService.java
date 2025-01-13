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
public interface RegisterService {

    /**
     * 회원 아이디 중복 여부를 확인합니다.
     *
     * @param id 회원 아이디
     * @return 중복되지 않으면 true, 중복되면 false
     */
    boolean checkId(String id);

    /**
     * 회원 정보를 데이터베이스에 저장합니다.
     *
     * @param member 회원 요청 데이터
     * @return 저장된 행의 개수 (정상적으로 저장되면 1)
     * @throws Exception 저장 중 에러 발생 시 예외 발생
     */
    int insertMember(MemberRequest member) throws Exception;

}
