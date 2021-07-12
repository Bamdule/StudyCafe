package com.bamdule.studycafe.entity.seatusage.repository;

import com.bamdule.studycafe.entity.seatusage.SeatUsage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatUsageRepository extends JpaRepository<SeatUsage, Integer>, SeatUsageRepositoryCustom {
    Optional<SeatUsage> findByMemberId(Integer memberId);
}
