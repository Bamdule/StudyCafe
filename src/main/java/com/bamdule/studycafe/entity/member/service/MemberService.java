package com.bamdule.studycafe.entity.member.service;

import com.bamdule.studycafe.entity.member.MemberTO;

public interface MemberService {

    MemberTO saveMember(MemberTO memberTO);
    MemberTO updateMember(MemberTO memberTO);

    String loginMember(String phone, String password);
}
