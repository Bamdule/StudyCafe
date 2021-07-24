package com.bamdule.studycafe.entity.seatusage.history;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(name = "start_dt", nullable = false)
    private LocalDateTime startDt;

    @Column(name = "end_dt")
    private LocalDateTime endDt;

    @Column(name = "study_minutes")
    Long studyMinutes;

    @Column(nullable = false)
    private String day;

    @Column(nullable = false)
    private String month;
}
