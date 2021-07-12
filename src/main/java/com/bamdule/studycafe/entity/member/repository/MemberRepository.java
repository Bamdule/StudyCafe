package com.bamdule.studycafe.entity.member.repository;

import com.bamdule.studycafe.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer>, MemberRepositoryCustom {

    public Member findMemberByPhone(String phone);
}
