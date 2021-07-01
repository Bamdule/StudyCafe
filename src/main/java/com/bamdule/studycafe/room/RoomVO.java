package com.bamdule.studycafe.room;

import com.bamdule.studycafe.seat.SeatVO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomVO {
    private Integer id;

    private String name;

    private List<SeatVO> seats = new ArrayList<>();
}
