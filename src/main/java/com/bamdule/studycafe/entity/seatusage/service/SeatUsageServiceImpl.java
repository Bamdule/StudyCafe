package com.bamdule.studycafe.entity.seatusage.service;

import com.bamdule.studycafe.entity.member.Member;
import com.bamdule.studycafe.entity.member.repository.MemberRepository;
import com.bamdule.studycafe.entity.seat.Seat;
import com.bamdule.studycafe.entity.seat.repository.SeatRepository;
import com.bamdule.studycafe.entity.seatusage.SeatUsage;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.seatusage.repository.SeatUsageRepository;
import com.bamdule.studycafe.exception.CustomException;
import com.bamdule.studycafe.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SeatUsageServiceImpl implements SeatUsageService {

    @Autowired
    private SeatUsageRepository seatUsageRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Override
    public SeatUsageVO saveSeatUsage(Integer memberId, Integer seatId) {

        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Optional<Seat> optionalSeat = seatRepository.findById(seatId);

        if (optionalMember.isEmpty()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_USER);
        }

        if (optionalSeat.isEmpty()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_SEAT);
        }

        if (seatUsageRepository.checkAvailableMember(memberId).isPresent()) {
            throw new CustomException(ExceptionCode.USER_ALREADY_USE);
        }

        if (seatUsageRepository.checkAvailableSeat(seatId).isEmpty()) {
            throw new CustomException(ExceptionCode.SEAT_ALREADY_IN_USE);
        } else {
            Member member = optionalMember.get();
            Seat seat = optionalSeat.get();

            LocalDateTime now = LocalDateTime.now();

            SeatUsage seatUsage = SeatUsage.builder()
                    .member(member)
                    .seat(seat)
                    .startDt(now)
                    .endDt(now.plusHours(5))
                    .build();

            seatUsageRepository.save(seatUsage);

            return SeatUsageVO.builder()
                    .id(seatUsage.getId())
                    .memberId(member.getId())
                    .memberName(member.getName())
                    .seatId(seat.getId())
                    .number(seat.getNumber())
                    .startDt(seatUsage.getStartDt())
                    .endDt(seatUsage.getEndDt())
                    .build();
        }
    }

    @Override
    public SeatUsageVO deleteSeatUsage(Integer memberId) {

        Optional<SeatUsage> optionalSeatUsage = seatUsageRepository.findByMemberId(memberId);

        if (optionalSeatUsage.isEmpty()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_USER_IN_USE);
        } else {
            SeatUsage seatUsage = optionalSeatUsage.get();

            SeatUsageVO seatUsageVO = SeatUsageVO.builder()
                    .id(seatUsage.getId())
                    .seatId(seatUsage.getSeat().getId())
                    .number(seatUsage.getSeat().getNumber())
                    .build();

            seatUsageRepository.delete(seatUsage);
            return seatUsageVO;
        }
    }

    @Override
    public SeatUsageVO updateSeatUsage(Integer memberId) {
        Optional<SeatUsage> optionalSeatUsage = seatUsageRepository.findByMemberId(memberId);

        if (optionalSeatUsage.isEmpty()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_USER_IN_USE);
        } else {

            SeatUsage seatUsage = optionalSeatUsage.get();
            LocalDateTime extensionTime = seatUsage.getEndDt().minusHours(1);

            if (isExtensionTime(extensionTime)) {
                seatUsage.setStartDt(seatUsage.getEndDt());
                seatUsage.setEndDt(seatUsage.getEndDt().plusHours(5));
                seatUsageRepository.flush();

                Optional<SeatUsageVO> optionalSeatUsageVO = seatUsageRepository.getSeatUsageByMemberId(memberId);

                return optionalSeatUsageVO.get();
            } else {
                throw new CustomException(ExceptionCode.INVALID_TIME_EXTEND, extensionTime + " ~ " + extensionTime.plusHours(1) + " 사이에 시간 연장을 할 수 있습니다.");
            }
        }

    }

    @Override
    public List<SeatUsageVO> getExpiredSeatUsages(LocalDateTime now) {
        return seatUsageRepository.getExpiredSeatUsages(now);
    }

    @Override
    public void deleteExpiredSeatUsages(LocalDateTime now) {
        seatUsageRepository.deleteExpiredSeatUsages(now);
    }

    @Override
    public SeatUsageVO getSeatUsageByMemberId(Integer memberId) {
        Optional<SeatUsageVO> optionalSeatUsage = seatUsageRepository.getSeatUsageByMemberId(memberId);

        if (optionalSeatUsage.isEmpty()) {
            throw new CustomException(ExceptionCode.NOT_FOUND_USER_IN_USE);
        } else {
            return optionalSeatUsage.get();
        }
    }

    private boolean isExtensionTime(LocalDateTime extensionTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime plus1Hour = now.plusHours(1);
        return now.isAfter(extensionTime) && extensionTime.isBefore(plus1Hour);
    }
}
