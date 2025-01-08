package com.ssginc.ewms.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberRequest {
    String memberId;
    String memberPw;
}
