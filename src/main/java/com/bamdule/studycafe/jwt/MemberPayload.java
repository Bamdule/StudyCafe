package com.bamdule.studycafe.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberPayload {
    private Integer memberId;
    private String memberName;
}
