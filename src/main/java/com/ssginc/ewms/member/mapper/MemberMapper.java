package com.ssginc.ewms.member.mapper;

import com.ssginc.ewms.member.dto.MemberRequest;
import com.ssginc.ewms.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 회원 관리를 위한 MyBatis Mapper 인터페이스
 *
 * <p>회원 정보를 데이터베이스와 연동하기 위해 사용됩니다.</p>
 *
 * <p>주요 기능:</p>
 * <ul>
 *     <li>회원 조회</li>
 *     <li>아이디, 이메일, 전화번호 중복 체크</li>
 *     <li>회원 정보 삽입</li>
 * </ul>
 */
@Mapper // resources/mapper/memberMapper.xml 파일과 연동
public interface MemberMapper {

    /**
     * 주어진 회원 아이디를 통해 회원 정보를 조회합니다.
     *
     * @param memberId 회원 아이디
     * @return 회원 정보 VO 객체
     */
    MemberVO selectMemberById(String memberId);

    /**
     * 이메일 중복 여부를 확인합니다.
     *
     * @param email 이메일 주소
     * @return 중복된 이메일이 존재하면 1, 그렇지 않으면 0
     */
    int checkMemberEmail(String email);

    /**
     * 아이디 중복 여부를 확인합니다.
     *
     * @param id 회원 아이디
     * @return 중복된 아이디가 존재하면 1, 그렇지 않으면 0
     */
    int checkMemberId(String id);

    /**
     * 전화번호 중복 여부를 확인합니다.
     *
     * @param phone 전화번호
     * @return 중복된 전화번호가 존재하면 1, 그렇지 않으면 0
     */
    int checkMemberPhone(String phone);

    /**
     * 회원 정보를 데이터베이스에 삽입합니다.
     *
     * @param member 회원 요청 데이터
     * @return 삽입된 행의 수 (정상적으로 삽입되면 1)
     */
    int insertMember(MemberRequest member);

    String selectMemberIdByEmail(String email);
}
