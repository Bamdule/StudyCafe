package com.bamdule.studycafe.member.service;

import com.bamdule.studycafe.member.MemberTO;
import com.bamdule.studycafe.member.MemberVO;

public interface MemberService {

    MemberTO saveMember(MemberTO memberTO);
    MemberTO updateMember(MemberTO memberTO);

}
