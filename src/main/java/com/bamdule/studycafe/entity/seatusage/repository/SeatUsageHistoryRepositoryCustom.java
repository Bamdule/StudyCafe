package com.bamdule.studycafe.entity.seatusage.repository;

import com.bamdule.studycafe.entity.seatusage.history.SeatUsageHistory;
import com.bamdule.studycafe.entity.seatusage.history.StudyDayVO;

import java.util.List;
import java.util.Optional;

public interface SeatUsageHistoryRepositoryCustom {
    public Optional<SeatUsageHistory> findLastSeatUsageHistory(Integer memberId);

    public List<StudyDayVO> getListStudyDay(String yyyyMM, Integer memberId);


}
