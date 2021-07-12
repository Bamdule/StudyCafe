package com.bamdule.studycafe.entity.studycafe.service;

import com.bamdule.studycafe.entity.room.RoomVO;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seatusage.SeatUsageTO;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.studycafe.StudyCafeVO;

import java.util.List;

public interface StudyCafeService {

    public List<StudyCafeVO> findAllStudyCafe();

    public List<RoomVO> findAllRoom(Integer studyCafeId);

    public List<SeatVO> findAllSeat(Integer roomId);
    public SeatUsageVO saveSeatUsage(Integer memberId, Integer seatId);

    public void deleteSeatUsage(Integer memberId);

    public SeatUsageVO updateSeatUsage(Integer memberId);


}
