package com.bamdule.studycafe.entity.member;

import com.bamdule.studycafe.common.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberVO {
    private Integer id;

    private String name;

    private String email;

    private String phone;

    private LocalDateTime joinDt;

    private Integer targetStudyHour;

    public String getJoinDtText() {
        return DateUtils.localDateTimeToString(this.joinDt);
    }
}
