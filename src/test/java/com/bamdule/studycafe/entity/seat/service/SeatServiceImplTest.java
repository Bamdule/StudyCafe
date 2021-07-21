package com.bamdule.studycafe.entity.seat.service;

import com.bamdule.studycafe.entity.room.Room;
import com.bamdule.studycafe.entity.seat.Seat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:test.properties")
class SeatServiceImplTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    EntityManager em;

    @Test
    public void findAllSeatTest() throws Exception {
        this.mockMvc.perform(get("/api/seat")
                .contentType(MediaType.APPLICATION_JSON)
                .param("roomId", "1"))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void findAllSeat() {
        Query query = em.createQuery("select s from Seat s");
        List resultList = query.getResultList();

        resultList.forEach(e -> {
            System.out.println(e);
        });
    }

    @Test
    @Commit
    public void saveSeats() {
        Room room = (Room) em.createQuery("select r from Room r where r.id = 1").getResultList().get(0);

        int number = 84;
        for (int r = 0; r < room.getHeight(); r++) {


            Seat seat = Seat.builder()
                    .row(r)
                    .col(17)
                    .number(number++)
                    .room(room)
                    .active(true)
                    .build();
            em.persist(seat);
        }

    }

}