package com.bamdule.studycafe.entity.seatusage.repository;

import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seatusage.SeatAvailability;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SeatUsageRepositoryCustom {

    public Optional<SeatUsageVO> getSeatUsageByMemberId(Integer memberId);

    public Optional<SeatVO> checkAvailableSeat(Integer seatId);

    public Optional<SeatVO> checkAvailableMember(Integer memberId);

    public List<SeatUsageVO> getExpiredSeatUsages(LocalDateTime now);
    
    public void deleteExpiredSeatUsages(LocalDateTime now);

    SeatAvailability getSeatAvailability(Integer roomId);

}
