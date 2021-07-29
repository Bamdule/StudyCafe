package com.bamdule.studycafe.entity.seatusage.history;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class StudyInfoVO {

    private String date;
    private List<StudyDayVO> studyDays;

    private Integer targetStudyHour = 0;

}
