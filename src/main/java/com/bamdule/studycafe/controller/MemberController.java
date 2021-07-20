package com.bamdule.studycafe.controller;

import com.bamdule.studycafe.exception.CustomException;
import com.bamdule.studycafe.exception.ExceptionCode;
import com.bamdule.studycafe.jwt.JWTUtils;
import com.bamdule.studycafe.entity.member.MemberTO;
import com.bamdule.studycafe.entity.member.service.MemberService;
import com.bamdule.studycafe.jwt.EmailPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.IdentityHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private JWTUtils jwtUtils;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping
    public ResponseEntity<MemberTO> saveMember(@Valid @RequestBody MemberTO memberTO) {

        EmailPayload emailPayload = jwtUtils.verifyEmailJWT(memberTO.getEmailCode());
        if (!emailPayload.getEmail().equals(memberTO.getEmail())) {
            throw new CustomException(ExceptionCode.INVALID_TOKEN);
        }
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

    @PostMapping(value = "/email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendMail(@RequestParam(value = "email") String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[StudyCafe] 인증 메일");

        EmailPayload emailPayload = new EmailPayload();
        emailPayload.setEmail(email);
        message.setText(jwtUtils.createEmailToken(emailPayload));
        mailSender.send(message);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity loginMember(String phone, String password) {
        Map<String, String> map = new IdentityHashMap<>();
        map.put("token", memberService.loginMember(phone, password));

        return ResponseEntity.ok(map);
    }

}

