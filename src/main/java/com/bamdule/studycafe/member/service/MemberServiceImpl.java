package com.bamdule.studycafe.member.service;

import com.bamdule.studycafe.exception.CustomException;
import com.bamdule.studycafe.exception.ErrorCode;
import com.bamdule.studycafe.member.Member;
import com.bamdule.studycafe.member.MemberTO;
import com.bamdule.studycafe.member.repository.MemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Override
//    @Transactional
//    public MemberTO joinMember(MemberTO memberTO) {
//        Member member = modelMapper.map(memberTO, Member.class);
//
//        if (member.getId() == null) {
//            if (duplicatePhoneCheck(member.getPhone()) != null) {
//                throw new RuntimeException("이미 가입한 휴대폰 번호입니다.");
//            }
//
//            member.setPassword(passwordEncoder.encode(memberTO.getPassword()));
//            member.setJoinDt(LocalDateTime.now());
//        }
//
//        memberRepository.save(member);
//        memberTO.setId(member.getId());
//
//        return memberTO;
//    }

    private Member duplicatePhoneCheck(String phone) {
        return memberRepository.findMemberByPhone(phone);
    }

    @Override
    public MemberTO saveMember(MemberTO memberTO) {

        Member member = modelMapper.map(memberTO, Member.class);


        member.setPassword(passwordEncoder.encode(memberTO.getPassword()));
        member.setJoinDt(LocalDateTime.now());
        memberRepository.save(member);

        memberTO.setId(member.getId());
        return memberTO;
    }

    @Override
    public MemberTO updateMember(MemberTO memberTO) {

        Optional<Member> OptionalMember = memberRepository.findById(memberTO.getId());

        if (OptionalMember.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        } else {
            Member updatedMember = OptionalMember.get();
            updatedMember.setName(memberTO.getName());

            memberRepository.save(updatedMember);

            MemberTO result = modelMapper.map(updatedMember, MemberTO.class);
            result.setPassword("");
            return result;
        }
    }
}
