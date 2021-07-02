package com.bamdule.studycafe.member;

import com.bamdule.studycafe.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDateTime;

@Component
public class MemberValidator {

    @Autowired
    private MemberRepository memberRepository;

    public void validateOfSave(MemberTO memberTO, Errors errors) {
        if (duplicatePhoneCheck(memberTO.getPhone()) != null) {
            errors.rejectValue("phone", "EXISTED_PHONE", "이미 사용중인 휴대폰번호입니다.");
        }
    }

    public void validateOfUpdate(MemberTO memberTO, Errors errors) {
        if (memberRepository.findById(memberTO.getId()).isEmpty()) {
            errors.rejectValue("id", "NOT_FOUND_USER", "존재하지 않는 회원입니다.");
        }
    }

    private Member duplicatePhoneCheck(String phone) {
        return memberRepository.findMemberByPhone(phone);
    }
}
