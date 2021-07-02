package com.bamdule.studycafe.seatusage.history;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(
        name = "seat_usage_history",
        indexes = {
                @Index(columnList = "member_id"),
                @Index(columnList = "start_dt")})
public class SeatUsageHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "seat_id", nullable = false)
    private Integer seatId;

    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "start_dt", nullable = false)
    LocalDateTime startDt;

    @Column(name = "end_dt")
    LocalDateTime endDt;
}
