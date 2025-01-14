package com.ssginc.ewms.member.service;

import com.ssginc.ewms.member.dto.MemberUpdateRequest;

public interface ModifyService {
    int updateMember(MemberUpdateRequest member);
}
