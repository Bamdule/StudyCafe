package com.bamdule.studycafe.entity.member;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaidMemberVO {

    private Integer memberId;

    private String memberName;

    private Integer remainingTime;

    private LocalDateTime paymentDt;

    private String productName;

    private Integer validDays;

    private boolean payment;
}
