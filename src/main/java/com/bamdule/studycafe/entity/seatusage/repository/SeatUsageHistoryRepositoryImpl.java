package com.bamdule.studycafe.entity.seatusage.repository;

import com.bamdule.studycafe.entity.seatusage.history.QSeatUsageHistory;
import com.bamdule.studycafe.entity.seatusage.history.SeatUsageHistory;
import com.bamdule.studycafe.entity.seatusage.history.StudyDayVO;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.bamdule.studycafe.entity.seatusage.history.QSeatUsageHistory.seatUsageHistory;

public class SeatUsageHistoryRepositoryImpl implements SeatUsageHistoryRepositoryCustom {

    @Autowired
    private EntityManager em;

    @Override
    public Optional<SeatUsageHistory> findLastSeatUsageHistory(Integer memberId) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        SeatUsageHistory findHistory = query
                .select(seatUsageHistory)
                .from(seatUsageHistory)
                .where(seatUsageHistory.memberId.eq(memberId))
                .orderBy(seatUsageHistory.id.desc())
                .fetchFirst();

        return Optional.ofNullable(findHistory);
    }

    @Override
    public List<StudyDayVO> getListStudyDay(String yyyyMM, Integer memberId) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        NumberExpression<Long> studyMinutesExpression = new CaseBuilder()
                .when(seatUsageHistory.studyMinutes.isNull()).then(0L)
                .otherwise(seatUsageHistory.studyMinutes.sum());

        return query
                .select(Projections.bean(
                        StudyDayVO.class,
                        seatUsageHistory.day,
                        studyMinutesExpression.as("studyMinutes")
                ))
                .from(seatUsageHistory)
                .where(
                        seatUsageHistory.month.eq(yyyyMM),
                        seatUsageHistory.memberId.eq(memberId)
                )
                .groupBy(seatUsageHistory.day)
                .orderBy(seatUsageHistory.day.asc())
                .fetch()
                ;
    }
}
