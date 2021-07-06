package com.bamdule.studycafe.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
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

        this.mockMvc.perform(put("/api/member/{id}",member.getId())
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

        this.mockMvc.perform(put("/api/member/5555",member.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberTO)))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    public Member generateMember() {
        Member member = Member.builder()
                .name("kim")
                .phone("01027371614")
                .password("1234")
                .joinDt(LocalDateTime.now())
                .build();

        em.persist(member);

        return member;
    }
}