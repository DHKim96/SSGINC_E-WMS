package com.ssginc.ewms.member.mapper;

import com.ssginc.ewms.member.vo.MemberVO;
import org.apache.ibatis.annotations.Mapper;


@Mapper //resources/mapper/memberMapper.xml
public interface MemberMapper {
    MemberVO selectMemberById(String memberId);

    int checkMemberEmail(String email);

    int checkMemberId(String id);
}
