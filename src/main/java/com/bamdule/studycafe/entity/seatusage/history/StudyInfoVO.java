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

    private String month;
    private List<StudyDayVO> studyDays;

}
