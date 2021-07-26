package com.bamdule.studycafe.entity.member.service;

import com.bamdule.studycafe.entity.member.MemberTO;
import com.bamdule.studycafe.entity.member.MemberVO;

public interface MemberService {

    MemberTO saveMember(MemberTO memberTO);

    MemberTO updateMember(MemberTO memberTO);

    String loginMember(String phone, String password);

    MemberVO getMemberById(Integer memberId);
}
