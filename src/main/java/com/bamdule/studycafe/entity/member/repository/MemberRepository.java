package com.bamdule.studycafe.entity.member.repository;

import com.bamdule.studycafe.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer>, MemberRepositoryCustom {

    public Optional<Member> findMemberByPhone(String phone);

    public Optional<Member> findMemberByEmail(String email);
}
