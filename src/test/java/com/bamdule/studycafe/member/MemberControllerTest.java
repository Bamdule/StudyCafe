package com.bamdule.studycafe.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test.yml")
class MemberControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    @Test
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
}