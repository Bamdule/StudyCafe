package com.bamdule.studycafe.seat.repository;

import com.bamdule.studycafe.seat.SeatVO;

import java.util.List;

public interface SeatRepositoryCustom {

    public List<SeatVO> findAllSeatByRoomId(Integer roomId);

}
