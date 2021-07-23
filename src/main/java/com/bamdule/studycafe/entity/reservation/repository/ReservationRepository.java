package com.bamdule.studycafe.entity.reservation.repository;

import com.bamdule.studycafe.entity.reservation.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>, ReservationRepositoryCustom {

    Optional<Reservation> findReservationByMemberId(Integer memberId);

    public void deleteReservationByMemberId(Integer memberId);

}
