package com.bamdule.studycafe.entity.seat.service;

import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seat.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public List<SeatVO> findAllSeatByRoomId(Integer roomId) {
        return seatRepository.findAllSeatByRoomId(roomId);
    }
}
