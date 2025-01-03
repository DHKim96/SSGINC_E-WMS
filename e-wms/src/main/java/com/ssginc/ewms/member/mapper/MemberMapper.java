package com.ssginc.ewms.member.mapper;

import org.apache.ibatis.annotations.Mapper;


@Mapper //resources/mapper/memberMapper.xml
public interface MemberMapper {
    //프로그래머가 필요한 db처리 crud를 설정하세요.
    //자동으로 /resources/mapper/memberMapper.xml에 설정한
    //sql문을 자동으로 실행해주고, 결과도 프로그래머가 원하는 타입으로
    //만들어서 준다.!!!
    //회원가입
    //insert into member values('아이디', '패스워드', '이름', '전화번호');
    //insert의 결과는 int(row의 개수)
    //insert의 입력은 vo
//    int insertMember(MemberVO memberVO);
//    MemberVO selectMemberById(String id);
//    List<MemberVO> selectMemberAll();
//    int updateMember(MemberVO memberVO);
//    int deleteMember(String id);
//    String selectMemberNameById(String id);
//    List<MemberVO> selectMemberByName(String name);
//    List<MemberVO> selectMembersByNameTel(MemberVO memberVO);
}
