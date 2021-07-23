package com.bamdule.studycafe.entity.reservation.service;

import com.bamdule.studycafe.entity.reservation.Reservation;
import com.bamdule.studycafe.entity.reservation.ReservationVO;
import com.bamdule.studycafe.entity.reservation.repository.ReservationRepository;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.seatusage.repository.SeatUsageRepository;
import com.bamdule.studycafe.exception.CustomException;
import com.bamdule.studycafe.exception.ExceptionCode;
import com.bamdule.studycafe.websocket.MessageType;
import com.bamdule.studycafe.websocket.WebSocketHandler;
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

    @Autowired
    private WebSocketHandler webSocketHandler;

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
    public ReservationVO saveReservation(Integer studyCafeId, Integer memberId) {

//        if (seatUsageRepository.getCountEmptySeatOfStudyCafe(studyCafeId) > 0) {
//            throw new CustomException(ExceptionCode.EXIST_AVAILABLE_SEATS);
//        }


        if (reservationRepository.findReservationByMemberId(memberId).isPresent()) {
            throw new CustomException(ExceptionCode.USER_ALREADY_RESERVATION);
        }

        Optional<SeatUsageVO> optionalSeatUsageVO = seatUsageRepository.getSeatUsageByMemberId(memberId);

        if (optionalSeatUsageVO.isEmpty()) {

            Reservation reservation = Reservation.builder()
                    .memberId(memberId)
                    .build();

            reservationRepository.save(reservation);
            
            Long countReservation = reservationRepository.getCountReservation();
            webSocketHandler.broadcast(MessageType.SAVE_RESERVATION, countReservation);
            this.executeReservation();

            return ReservationVO
                    .builder()
                    .id(reservation.getId())
                    .memberId(reservation.getMemberId())
                    .totalReservation(countReservation)
                    .build();

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
        webSocketHandler.broadcast(MessageType.DELETE_RESERVATION, reservationRepository.getCountReservation());
        this.executeReservation();
    }

    public boolean checkReservationMember(ReservationVO reservation, Integer memberId) {
        //예약회원인가?
        if (reservation.getMemberId().equals(memberId)) {
            //예약 시간이만료되지 않았는가?
            if (reservation.getValidDt() != null && reservation.getValidDt().isAfter(LocalDateTime.now())) {
                deleteReservation(memberId);
                return true;
            } else {
                throw new CustomException(ExceptionCode.RESERVATION_TIME_EXPIRATION);
            }
        } else {
            return false;
        }
    }

    @Override
    public Long getCountReservations() {
        return reservationRepository.getCountReservation();
    }


}
