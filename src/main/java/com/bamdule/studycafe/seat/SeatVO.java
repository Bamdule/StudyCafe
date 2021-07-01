package com.bamdule.studycafe.seat;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeatVO {

    private Integer id;

    private Integer number;

    private SeatStatus status;

    private Integer memberId;

    private String memberName;

    private LocalDateTime startDt;
}
