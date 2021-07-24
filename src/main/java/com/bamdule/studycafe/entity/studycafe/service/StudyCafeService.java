package com.bamdule.studycafe.entity.studycafe.service;

import com.bamdule.studycafe.entity.room.RoomVO;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;

import java.util.List;

public interface StudyCafeService {

//    public List<StudyCafeVO> findAllStudyCafe();

//    public List<RoomVO> findAllRoom(Integer studyCafeId);
//
//    public List<SeatVO> findAllSeat(Integer roomId);

    public SeatUsageVO saveSeatUsage(Integer memberId, Integer roomId, Integer seatId);

    public SeatUsageVO deleteSeatUsage(Integer memberId);

    public SeatUsageVO updateSeatUsage(Integer memberId);

    public SeatUsageVO getSeatUsageByMemberId(Integer memberId);


}
