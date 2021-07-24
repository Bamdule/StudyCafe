package com.bamdule.studycafe.entity.seatusage.history;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StudyDayVO {
    private String day;
    private Long studyMinutes;
}
