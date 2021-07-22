package com.bamdule.studycafe.entity.reservation.repository;

import com.bamdule.studycafe.entity.reservation.Reservation;

import java.util.Optional;

public interface ReservationRepositoryCustom {

    Optional<Reservation> findFirstReservation();
}
