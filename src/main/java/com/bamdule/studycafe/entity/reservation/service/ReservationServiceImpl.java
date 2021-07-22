package com.bamdule.studycafe.entity.reservation.service;

import com.bamdule.studycafe.entity.reservation.Reservation;
import com.bamdule.studycafe.entity.reservation.ReservationVO;
import com.bamdule.studycafe.entity.reservation.repository.ReservationRepository;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.seatusage.repository.SeatUsageRepository;
import com.bamdule.studycafe.entity.seatusage.service.SeatUsageService;
import com.bamdule.studycafe.exception.CustomException;
import com.bamdule.studycafe.exception.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatUsageRepository seatUsageRepository;

    @Override
    public void executeReservation() {
        Optional<Reservation> optionalReservation = reservationRepository.findFirstReservation();

        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();

            if (reservation.getValidDt() == null) {
                reservation.setValidDt(LocalDateTime.now().plusMinutes(10));
                reservationRepository.save(reservation);
            } else {
                if (reservation.getValidDt().isBefore(LocalDateTime.now())) {
                    this.deleteReservation(reservation.getMemberId());
                }
            }
        }
    }

    @Override
    public void saveReservation(Integer memberId) {
        Optional<SeatUsageVO> optionalSeatUsageVO = seatUsageRepository.getSeatUsageByMemberId(memberId);

        if (reservationRepository.findReservationByMemberId(memberId).isPresent()) {
            throw new CustomException(ExceptionCode.USER_ALREADY_RESERVATION);
        }

        if (optionalSeatUsageVO.isEmpty()) {

            Reservation reservation = Reservation.builder()
                    .memberId(memberId)
                    .build();

            reservationRepository.save(reservation);
        } else {
            throw new CustomException(ExceptionCode.USER_ALREADY_SEAT_USE);
        }
    }

    @Override
    public Optional<ReservationVO> getFirstReservationVO() {
        Optional<Reservation> optionalReservation = reservationRepository.findFirstReservation();
        if (optionalReservation.isPresent()) {
            Reservation reservation = optionalReservation.get();
            return Optional.ofNullable(
                    ReservationVO
                            .builder()
                            .id(reservation.getId())
                            .memberId(reservation.getMemberId())
                            .validDt(reservation.getValidDt())
                            .build());
        } else {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public void deleteReservation(Integer memberId) {
        reservationRepository.deleteReservationByMemberId(memberId);
        this.executeReservation();
    }
}
