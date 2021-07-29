package com.bamdule.studycafe.entity.member.service;

import com.bamdule.studycafe.entity.member.MemberTO;
import com.bamdule.studycafe.entity.member.MemberVO;

public interface MemberService {

    MemberTO saveMember(MemberTO memberTO);

    void updateMember(

            Integer memberId,
            Integer targetStudyHour,
            String password);

    String loginMember(String phone, String password);

    MemberVO getMemberById(Integer memberId);
}
