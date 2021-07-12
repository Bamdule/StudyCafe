package com.bamdule.studycafe.entity.seat.repository;

import com.bamdule.studycafe.entity.seat.SeatVO;

import java.util.List;

public interface SeatRepositoryCustom {

    public List<SeatVO> findAllSeatByRoomId(Integer roomId);

}
