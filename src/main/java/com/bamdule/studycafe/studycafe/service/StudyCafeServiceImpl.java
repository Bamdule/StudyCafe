package com.bamdule.studycafe.studycafe.service;

import com.bamdule.studycafe.room.RoomVO;
import com.bamdule.studycafe.seat.SeatVO;
import com.bamdule.studycafe.studycafe.StudyCafeVO;
import com.bamdule.studycafe.studycafe.repository.StudyCafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyCafeServiceImpl implements StudyCafeService {

    @Autowired
    private StudyCafeRepository studyCafeRepository;

    @Override
    public List<StudyCafeVO> findAllStudyCafe() {
        return studyCafeRepository.findAllStudyCafe();

    }

    @Override
    public List<RoomVO> findAllRoom(Integer studyCafeId) {
        return studyCafeRepository.findAllRoom(studyCafeId);
    }

    @Override
    public List<SeatVO> findAllSeat(Integer roomId) {
        return studyCafeRepository.findAllSeat(roomId);
    }
}
