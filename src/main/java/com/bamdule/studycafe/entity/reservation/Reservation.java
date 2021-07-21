package com.bamdule.studycafe.entity.reservation;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "member_id", nullable = false, unique = true)
    private Integer memberId;

    @Column(name = "valid_dt")
    private LocalDateTime validDt;

}
