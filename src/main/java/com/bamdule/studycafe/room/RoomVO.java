package com.bamdule.studycafe.room;

import com.bamdule.studycafe.seat.SeatVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomVO {
    private Integer id;

    private String name;
}
