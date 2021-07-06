package com.bamdule.studycafe.seat.service;

import com.bamdule.studycafe.seat.SeatVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test.properties")
class SeatServiceImplTest {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void findAllSeatTest() throws Exception {
        this.mockMvc.perform(get("/api/seat")
                .contentType(MediaType.APPLICATION_JSON)
                .param("roomId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

}