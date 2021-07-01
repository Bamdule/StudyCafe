package com.bamdule.studycafe.studycafe.repository;

import com.bamdule.studycafe.room.RoomVO;
import com.bamdule.studycafe.seat.SeatVO;
import com.bamdule.studycafe.studycafe.StudyCafeVO;

import java.util.List;

public interface StudyCafeRepositoryCustom {
    public List<StudyCafeVO> findAllStudyCafe();

    public List<RoomVO> findAllRoom(Integer studyCafeId);

    public List<SeatVO> findAllSeat(Integer roomId);
}
