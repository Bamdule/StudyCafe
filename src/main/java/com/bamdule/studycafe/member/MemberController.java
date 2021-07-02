package com.bamdule.studycafe.member;

import com.bamdule.studycafe.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberValidator memberValidator;

    @PostMapping
    public ResponseEntity saveMember(@Valid @RequestBody MemberTO memberTO, Errors errors) {

        memberValidator.validateOfSave(memberTO, errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.ok(memberService.saveMember(memberTO));
    }

    @PutMapping(value = "/{memberId}")
    public ResponseEntity updateMember(@PathVariable Integer memberId, @Valid @RequestBody MemberTO memberTO) {

        memberTO.setId(memberId);
//        memberValidator.validateOfUpdate(memberTO, errors);
//        if (errors.hasErrors()) {
//            return ResponseEntity.badRequest().body(errors);
//        }

        return ResponseEntity.ok(memberService.updateMember(memberTO));
    }
}
