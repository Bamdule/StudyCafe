package com.bamdule.studycafe.entity.seat.repository;

import com.bamdule.studycafe.entity.seat.Seat;
import com.bamdule.studycafe.entity.seat.SeatVO;

import java.util.List;
import java.util.Optional;

public interface SeatRepositoryCustom {

    public Optional<Seat> findSeat(Integer roomId, Integer seatId);

    public List<SeatVO> findAllSeat(Integer roomId);
}
