package com.bamdule.studycafe.entity.member;

import com.bamdule.studycafe.entity.serverconfig.ServerConfig;
import com.bamdule.studycafe.exception.CustomException;
import com.bamdule.studycafe.exception.ExceptionCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test.properties")
@Transactional
class MemberControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EntityManager em;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원 가입 성공")
    public void saveMemberTest() throws Exception {

        MemberTO memberTO = MemberTO.builder()
                .name("kim")
                .password("1234")
                .phone("01027371614")
                .build();

        this.mockMvc.perform(post("/api/member")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberTO)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("입력값이 누락 되었을 경우 실패")
    public void saveMemberBadRequestEmptyInput() throws Exception {

        MemberTO memberTO = MemberTO.builder()
                .name("kim")
                .phone("01027371614")
                .build();

        this.mockMvc.perform(post("/api/member")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("회원가입 시 이미 존재 하는 휴대폰 번호 일 경우")
    public void existedPhone() throws Exception {
        Member member = generateMember();

        MemberTO memberTO = MemberTO.builder()
                .name("kim")
                .password("1234")
                .phone(member.getPhone())
                .build();

        this.mockMvc.perform(post("/api/member")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberTO)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @DisplayName("회원 수정 성공")
    public void updateMember() throws Exception {
        Member member = generateMember();
        member.setName("ko");

        MemberTO memberTO = modelMapper.map(member, MemberTO.class);

        this.mockMvc.perform(put("/api/member/{id}", member.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberTO)))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("존재하지 않는 회원 수정 실패")
    public void updateMemberBadRequestNotFound() throws Exception {
        Member member = generateMember();
        member.setName("ko");

        MemberTO memberTO = modelMapper.map(member, MemberTO.class);

        this.mockMvc.perform(put("/api/member/5555", member.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberTO)))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    @DisplayName("회원 로그인 테스트")
    public void loginMemberTest() throws Exception {
        Member member = generateMember();

        this.mockMvc.perform(post("/api/member/login")
                .param("phone", member.getPhone())
                .param("password", "1234")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("회원 인증")
    public void verifyJWTMemberTest() throws Exception {
        this.mockMvc.perform(post("/api/member/seatUsage")
                .header("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiZHF3ZXF3ZSIsInN1YiI6Im1lbWJlciIsImlkIjo1LCJwaG9uZSI6IjAxMDI3MzcxNjE0IiwiZXhwIjoxNjI1NjUwMDUzfQ.BH94t-u3CcGn-xGuvhDHukxOpRsprSmlWmtTL6o0Vmc")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void test() {
        Assertions.assertTrue(passwordEncoder.matches("1234", passwordEncoder.encode("1234")));
    }

    public Member generateMember() {
        Member member = Member.builder()
                .name("kim")
                .phone("01027371614")
                .password(passwordEncoder.encode("1234"))
                .joinDt(LocalDateTime.now())
                .build();

        em.persist(member);

        return member;
    }

    @Test
    @DisplayName("인증 메일 전송")
    public void sendEmail() throws Exception {
        this.mockMvc.perform(post("/api/member/email")
                .contentType(MediaType.APPLICATION_JSON)
                .param("email", "Bamdule5@gmail.com"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void tokenTest() {

        String key = "bamdule_member_key";
        Map<String, Object> data = new IdentityHashMap<>();
        String jwt = createToken(data, key);

        Map<String, Object> claimMap = null;
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key.getBytes("UTF-8")) // Set Key
                    .parseClaimsJws(jwt) // 파싱 및 검증, 실패 시 에러
                    .getBody();

            claimMap = claims;

        } catch (ExpiredJwtException e) { // 토큰이 만료되었을 경우
            throw new CustomException(ExceptionCode.TOKEN_EXPIRATION, e);
        } catch (Exception e) {
            throw new CustomException(ExceptionCode.INVALID_TOKEN);
        }
    }

    private String createToken(Map<String, Object> data, String key) {

        //Header 부분 설정
        Map<String, Object> headers = new IdentityHashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        //payload 부분 설정
        Map<String, Object> payloads = data;


        Long expiredTime = 1000 * 60L * 5L; //
//        Long expiredTime = 1000 * 60L;

        Date ext = new Date(); // 토큰 만료 시간
        ext.setTime(ext.getTime() + expiredTime);

        // 토큰 Builder
        String jwt = Jwts.builder()
                .setHeader(headers) // Headers 설정
                .setClaims(payloads) // Claims 설정
                .setSubject("user")
                .setExpiration(ext) // 토큰 만료 시간 설정
                .signWith(SignatureAlgorithm.HS256, key.getBytes()) // HS256과 Key로 Sign
                .compact(); // 토큰 생성

        return jwt;
    }
}