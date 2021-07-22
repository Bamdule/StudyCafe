package com.bamdule.studycafe.entity.reservation.service;

import com.bamdule.studycafe.entity.reservation.ReservationVO;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ReservationService {

    public void executeReservation();

    public void saveReservation(Integer memberId);

    public Optional<ReservationVO> getFirstReservationVO();

    public void deleteReservation(Integer memberId);
}
