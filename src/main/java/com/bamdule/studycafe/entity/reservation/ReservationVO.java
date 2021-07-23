package com.bamdule.studycafe.entity.reservation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationVO {

    private Integer id;

    private Integer memberId;

    private LocalDateTime validDt;

    private Long totalReservation = 0L;
}
