package com.bamdule.studycafe.entity.reservation.repository;

import com.bamdule.studycafe.entity.reservation.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepositoryCustom {

    public List<Reservation> getListReservationByLimit(Long limit);

    Long getCountReservation();
}
