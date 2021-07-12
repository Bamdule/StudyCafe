package com.bamdule.studycafe.entity.member;

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

    private String phone;

    private Integer remainingTime;

    private Integer AvailableDays;

    private Integer productId;

    private String productName;

    private LocalDateTime paymentDt;

    private LocalDateTime lastUsedDt;
}
