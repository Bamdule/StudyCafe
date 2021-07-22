package com.bamdule.studycafe.entity.seatusage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatAvailability {
    private int usedSeats = 0;
    private int unusedSeats = 0;
    private int limitedSeats = 0;
}
