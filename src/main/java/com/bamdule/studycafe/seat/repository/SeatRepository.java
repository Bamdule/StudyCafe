package com.bamdule.studycafe.seat.repository;

import com.bamdule.studycafe.seat.seatusage.SeatUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<SeatUsage, Integer>, SeatRepositoryCustom {
}
