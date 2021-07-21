package com.bamdule.studycafe.entity.seatusage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatAvailability {
    private int usedSeats;
    private int unusedSeats;
    private int limitedSeats;
}
