package com.bamdule.studycafe.entity.seat;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeatVO {

    private Integer id;

    private Integer number;

    private SeatStatus status;

}
