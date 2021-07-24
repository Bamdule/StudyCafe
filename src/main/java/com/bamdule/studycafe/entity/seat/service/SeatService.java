package com.bamdule.studycafe.entity.seat.service;

import com.bamdule.studycafe.entity.seat.SeatVO;

import java.util.List;

public interface SeatService {
   public List<SeatVO> findAllSeat(Integer roomId);
}
