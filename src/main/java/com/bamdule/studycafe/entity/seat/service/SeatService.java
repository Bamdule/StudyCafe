package com.bamdule.studycafe.entity.seat.service;

import com.bamdule.studycafe.entity.seat.SeatVO;

import java.util.List;

public interface SeatService {

    List<SeatVO> findAllSeatByRoomId(Integer roomId);


}
