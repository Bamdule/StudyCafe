package com.bamdule.studycafe.entity.studycafe.repository;

import com.bamdule.studycafe.entity.room.RoomVO;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.studycafe.StudyCafeVO;

import java.util.List;

public interface StudyCafeRepositoryCustom {
    public List<StudyCafeVO> findAllStudyCafe();

    public List<RoomVO> findAllRoom(Integer studyCafeId);

    public List<SeatVO> findAllSeat(Integer roomId);

    public SeatVO checkAvailableSeat(Integer roomId, Integer seatId);
}
