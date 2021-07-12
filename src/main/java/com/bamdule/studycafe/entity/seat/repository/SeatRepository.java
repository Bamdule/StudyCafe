package com.bamdule.studycafe.entity.seat.repository;

import com.bamdule.studycafe.entity.seat.Seat;
import com.bamdule.studycafe.entity.seatusage.SeatUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Integer>, SeatRepositoryCustom {
}
