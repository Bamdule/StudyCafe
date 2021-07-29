package com.bamdule.studycafe.entity.studycafe.service;

import com.bamdule.studycafe.entity.member.AllInfoVO;
import com.bamdule.studycafe.entity.member.service.MemberService;
import com.bamdule.studycafe.entity.reservation.ReservationVO;
import com.bamdule.studycafe.entity.reservation.service.ReservationService;
import com.bamdule.studycafe.entity.room.RoomVO;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.seatusage.history.StudyInfoVO;
import com.bamdule.studycafe.entity.seatusage.service.SeatUsageService;
import com.bamdule.studycafe.exception.CustomException;
import com.bamdule.studycafe.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class StudyCafeServiceImpl implements StudyCafeService {


    @Autowired
    private SeatUsageService seatUsageService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private MemberService memberService;

    @Override
    public SeatUsageVO saveSeatUsage(Integer memberId, Integer roomId, Integer seatId) {
        Optional<ReservationVO> optionalReservationVO = reservationService.getFirstReservationVO();

        //예약자가 있는가?
        if (optionalReservationVO.isPresent()) {
            //현재 회원이 예약자인가?
            if (reservationService.checkReservationMember(optionalReservationVO.get(), memberId)) {
                return seatUsageService.saveSeatUsage(memberId, roomId, seatId);
            } else {
                throw new CustomException(ExceptionCode.EXIST_RESERVATION_USER);
            }

        } else {
            return seatUsageService.saveSeatUsage(memberId, roomId, seatId);
        }

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

    @Override
    public AllInfoVO getAllInfo(Integer memberId) {

        return AllInfoVO.builder()
                .seatUsage(seatUsageService.getSeatUsageByMemberId(memberId))
                .member(memberService.getMemberById(memberId))
                .build();
    }

    @Override
    public StudyInfoVO getStudyInfo(Integer memberId, String studyDate) {
        return seatUsageService.getStudyInfo(
                memberId,
                LocalDate.parse(studyDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        );
    }
}
