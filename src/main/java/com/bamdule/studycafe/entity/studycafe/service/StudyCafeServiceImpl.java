package com.bamdule.studycafe.entity.studycafe.service;

import com.bamdule.studycafe.entity.room.RoomVO;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seatusage.SeatUsageTO;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.seatusage.repository.SeatUsageRepository;
import com.bamdule.studycafe.entity.seatusage.service.SeatUsageService;
import com.bamdule.studycafe.entity.studycafe.StudyCafeVO;
import com.bamdule.studycafe.entity.studycafe.repository.StudyCafeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyCafeServiceImpl implements StudyCafeService {

    @Autowired
    private StudyCafeRepository studyCafeRepository;

    @Autowired
    private SeatUsageService seatUsageService;

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

    @Override
    public SeatUsageVO saveSeatUsage(Integer memberId, Integer seatId) {
        return seatUsageService.saveSeatUsage(memberId, seatId);
    }

    @Override
    public SeatUsageVO deleteSeatUsage(Integer memberId) {
        return seatUsageService.deleteSeatUsage(memberId);
    }

    @Override
    public SeatUsageVO updateSeatUsage(Integer memberId) {
        return seatUsageService.updateSeatUsage(memberId);
    }

    @Override
    public SeatUsageVO getSeatUsageByMemberId(Integer memberId) {
        return seatUsageService.getSeatUsageByMemberId(memberId);
    }
}
