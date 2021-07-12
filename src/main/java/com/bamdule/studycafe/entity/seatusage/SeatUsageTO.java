package com.bamdule.studycafe.entity.seatusage;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SeatUsageTO {

    private Integer seatId;

    private Integer memberId;
}
