package com.bamdule.studycafe.entity.seatusage;

import com.bamdule.studycafe.entity.seat.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatUsageVO {

    private Integer id;

    private Integer seatId;

    private Integer number;

    private SeatStatus status;

    private Integer memberId;

    private String memberName;

    private LocalDateTime startDt;
    private LocalDateTime endDt;

}
