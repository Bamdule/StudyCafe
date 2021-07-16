package com.bamdule.studycafe.entity.seat;

import com.bamdule.studycafe.entity.seatusage.SeatStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeatVO {

    private Integer id;

    private Integer number;

    private String status;

    private Integer memberId;

    private String memberName;

    private LocalDateTime startDt;
    private LocalDateTime endDt;

    private Integer row;

    private Integer col;


}
