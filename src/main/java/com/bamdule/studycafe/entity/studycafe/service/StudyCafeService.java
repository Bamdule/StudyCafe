package com.bamdule.studycafe.entity.studycafe.service;

import com.bamdule.studycafe.entity.member.AllInfoVO;
import com.bamdule.studycafe.entity.room.RoomVO;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.seatusage.history.StudyInfoVO;

import java.util.List;

public interface StudyCafeService {

    public SeatUsageVO saveSeatUsage(Integer memberId, Integer roomId, Integer seatId);

    public SeatUsageVO deleteSeatUsage(Integer memberId);

    public SeatUsageVO updateSeatUsage(Integer memberId);

    public SeatUsageVO getSeatUsageByMemberId(Integer memberId);

    public AllInfoVO getAllInfo(Integer memberId);

    public StudyInfoVO getStudyInfo(Integer memberId, String date);

}
