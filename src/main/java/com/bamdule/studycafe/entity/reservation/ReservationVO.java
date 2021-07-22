package com.bamdule.studycafe.entity.reservation;

import lombok.*;

import javax.persistence.Column;
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
}
