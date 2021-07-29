package com.bamdule.studycafe.entity.member;

import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.seatusage.history.StudyDayVO;
import com.bamdule.studycafe.entity.seatusage.history.StudyInfoVO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllInfoVO {


    private MemberVO member;

    private SeatUsageVO seatUsage;
}
