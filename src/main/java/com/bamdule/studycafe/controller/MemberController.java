package com.bamdule.studycafe.controller;

import com.bamdule.studycafe.common.JWTUtils;
import com.bamdule.studycafe.entity.member.MemberTO;
import com.bamdule.studycafe.entity.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.IdentityHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JWTUtils jwtUtils;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity loginMember(String phone, String password) {
        System.out.println(phone);
        System.out.println(password);

        Map<String, String> map = new IdentityHashMap<>();
        map.put("token", memberService.loginMember(phone, password));

        return ResponseEntity.ok(map);
    }

    @PostMapping(value = "/seatUsage")
    public String seatSelection(@RequestHeader Map<String, Object> requestHeader) throws UnsupportedEncodingException {

        String accessToken = (String) requestHeader.get("authorization");

        jwtUtils.verifyJWT(accessToken);

        return "success";
    }
}

//SignatureException : 토큰을 수정할 경우
//