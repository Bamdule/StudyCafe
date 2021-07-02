package com.bamdule.studycafe.member.repository;

import com.bamdule.studycafe.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    public Member findMemberByPhone(String phone);
}
