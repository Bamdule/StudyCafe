package com.bamdule.studycafe.member;

import com.bamdule.studycafe.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @PostMapping
    public ResponseEntity<MemberTO> saveMember(@Valid @RequestBody MemberTO memberTO) {

        MemberTO newMember = memberService.saveMember(memberTO);
        return ResponseEntity.ok(newMember);
    }

    @PutMapping(value = "/{memberId}")
    public ResponseEntity<MemberTO> updateMember(
            @PathVariable Integer memberId,
            @Valid @RequestBody MemberTO memberTO
    ) {

        memberTO.setId(memberId);
        MemberTO updatedMember = memberService.updateMember(memberTO);
        return ResponseEntity.ok(updatedMember);
    }
}
