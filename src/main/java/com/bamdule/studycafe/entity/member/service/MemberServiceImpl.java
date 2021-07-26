package com.bamdule.studycafe.entity.member.service;

import com.bamdule.studycafe.entity.member.MemberVO;
import com.bamdule.studycafe.jwt.JWTUtils;
import com.bamdule.studycafe.entity.member.PaidMemberVO;
import com.bamdule.studycafe.exception.CustomException;
import com.bamdule.studycafe.exception.ExceptionCode;
import com.bamdule.studycafe.entity.member.Member;
import com.bamdule.studycafe.entity.member.MemberTO;
import com.bamdule.studycafe.entity.member.repository.MemberRepository;
import com.bamdule.studycafe.jwt.MemberPayload;
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

    @Autowired
    private JWTUtils jwtUtils;

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

    @Override
    public MemberTO saveMember(MemberTO memberTO) {
        if (memberRepository.findMemberByEmail(memberTO.getEmail()).isPresent()) {
            throw new CustomException(ExceptionCode.DUPLICATED_EMAIL);
        }

        if (memberRepository.findMemberByPhone(memberTO.getPhone()).isPresent()) {
            throw new CustomException(ExceptionCode.DUPLICATED_PHONE_NUMBER);
        }

        Member member = modelMapper.map(memberTO, Member.class);

        member.setPassword(passwordEncoder.encode(memberTO.getPassword()));
        member.setJoinDt(LocalDateTime.now());
        memberRepository.save(member);

        memberTO.setId(member.getId());
        memberTO.setJoinDt(member.getJoinDt());
        return memberTO;
    }

    @Override
    public MemberTO updateMember(MemberTO memberTO) {

        Optional<Member> OptionalMember = memberRepository.findById(memberTO.getId());

        if (OptionalMember.isEmpty()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_USER);
        } else {
            Member updatedMember = OptionalMember.get();
            updatedMember.setName(memberTO.getName());

            memberRepository.save(updatedMember);

            MemberTO result = modelMapper.map(updatedMember, MemberTO.class);
            result.setPassword("");
            return result;
        }
    }

    @Override
    public String loginMember(String phone, String password) {
        Optional<Member> optionalMember = memberRepository.findMemberByPhone(phone);

        if (optionalMember.isEmpty()) {
            throw new CustomException(ExceptionCode.LOGIN_FAILED);
        }
        Member member = optionalMember.get();

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new CustomException(ExceptionCode.LOGIN_FAILED);
        }

        PaidMemberVO paidMemberVO = memberRepository.findPaidMemberById(member.getId());

        System.out.println(paidMemberVO);


        MemberPayload memberPayload = new MemberPayload();
        memberPayload.setMemberId(paidMemberVO.getMemberId());
        memberPayload.setMemberName(paidMemberVO.getMemberName());

        return jwtUtils.createMemberToken(memberPayload);
    }

    @Override
    public MemberVO getMemberById(Integer memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return MemberVO.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .phone(member.getPhone())
                    .name(member.getName())
                    .joinDt(member.getJoinDt())
                    .targetStudyHour(member.getTargetStudyHour())
                    .build();

        } else {
            throw new CustomException(ExceptionCode.NOT_FOUND_USER);
        }
    }

}
