package com.bamdule.studycafe.entity.seatusage.service;

import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatUsageService {
    public SeatUsageVO saveSeatUsage(Integer memberId, Integer seatId);

    public void deleteSeatUsage(Integer memberId);

    public SeatUsageVO updateSeatUsage(Integer memberId);

    public void deleteExpiredSeatUsages(LocalDateTime now);
}
