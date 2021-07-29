package com.bamdule.studycafe.entity.targetstudy;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"member_id", "date"}
                )
        }
)
public class TargetStudy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "member_id")
    private Integer memberId;

    private LocalDate date;

    @Column(name = "target_study_hour", nullable = false)
    private Integer targetStudyHour;


}
