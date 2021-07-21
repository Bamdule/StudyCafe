package com.bamdule.studycafe.entity.seatusage.service;

import com.bamdule.studycafe.entity.seatusage.SeatAvailability;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;

import java.time.LocalDateTime;
import java.util.List;

public interface SeatUsageService {
    public SeatUsageVO saveSeatUsage(Integer memberId, Integer seatId);

    public SeatUsageVO deleteSeatUsage(Integer memberId);

    public SeatUsageVO updateSeatUsage(Integer memberId);

    public List<SeatUsageVO> getExpiredSeatUsages(LocalDateTime now);

    public void deleteExpiredSeatUsages(LocalDateTime now);

    public SeatUsageVO getSeatUsageByMemberId(Integer memberId);

    public SeatAvailability getSeatAvailability(Integer roomId);

}
