package com.bamdule.studycafe.entity.member.repository;

import com.bamdule.studycafe.entity.member.PaidMemberVO;

public interface MemberRepositoryCustom {

    public PaidMemberVO findPaidMemberById(Integer id);

}
