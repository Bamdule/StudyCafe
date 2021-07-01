package com.bamdule.studycafe.seat.service;

import com.bamdule.studycafe.seat.SeatVO;

import java.util.List;

public interface SeatService {

    List<SeatVO> findAllSeatByRoomId(Integer roomId);


}
