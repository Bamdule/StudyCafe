package com.bamdule.studycafe.entity.seatusage.repository;

import com.bamdule.studycafe.entity.seatusage.history.SeatUsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatUsageHistoryRepository extends JpaRepository<SeatUsageHistory, Integer>, SeatUsageHistoryRepositoryCustom {
}
