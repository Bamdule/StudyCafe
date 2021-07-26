package com.bamdule.studycafe.entity.seatusage.repository;

import com.bamdule.studycafe.entity.member.QMember;
import com.bamdule.studycafe.entity.room.QRoom;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seatusage.SeatAvailability;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.bamdule.studycafe.entity.seatusage.history.QSeatUsageHistory;
import com.bamdule.studycafe.entity.seatusage.history.SeatUsageHistory;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.bamdule.studycafe.entity.member.QMember.member;
import static com.bamdule.studycafe.entity.room.QRoom.*;
import static com.bamdule.studycafe.entity.seat.QSeat.seat;
import static com.bamdule.studycafe.entity.seatusage.QSeatUsage.seatUsage;
import static com.bamdule.studycafe.entity.seatusage.history.QSeatUsageHistory.seatUsageHistory;

public class SeatUsageRepositoryImpl implements SeatUsageRepositoryCustom {

    @Autowired
    private EntityManager em;

    @Override
    public Optional<SeatUsageVO> getSeatUsageByMemberId(Integer memberId) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        SeatUsageVO seatUsageVO = query
                .select(Projections.bean(
                        SeatUsageVO.class,
                        seatUsage.id,
                        seatUsage.seat.id.as("seatId"),
                        seatUsage.seat.number,
//                        seatUsage.member.id.as("memberId"),
//                        seatUsage.member.name.as("memberName"),
                        seatUsage.startDt,
                        seatUsage.endDt
                ))
                .from(seatUsage)
                .join(seatUsage.seat, seat)
                .join(seatUsage.member, member)
                .where(seatUsage.member.id.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(seatUsageVO);
    }

    @Override
    public Optional<SeatVO> checkAvailableSeat(Integer seatId) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        SeatVO seatVO = query
                .select(Projections.bean(
                        SeatVO.class,
                        seat.id,
                        seat.number
                ))
                .from(seat)
                .leftJoin(seatUsage).on(seatUsage.seat.id.eq(seat.id))
                .where(
                        seat.id.eq(seatId),
                        seatUsage.member.id.isNull()
                )
                .fetchOne();

        return Optional.ofNullable(seatVO);
    }

    @Override
    public Optional<SeatVO> checkAvailableMember(Integer memberId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        SeatVO seatVO = query
                .select(Projections.bean(
                        SeatVO.class,
                        seat.id,
                        seat.number
                ))
                .from(seatUsage)
                .where(seatUsage.member.id.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(seatVO);
    }

    @Override
    public List<SeatUsageVO> getExpiredSeatUsages(LocalDateTime now) {

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .select(Projections.bean(
                        SeatUsageVO.class,
                        seatUsage.id,
                        seatUsage.seat.id.as("seatId"),
                        seatUsage.startDt,
                        seatUsage.endDt
                ))
                .from(seatUsage)
                .where(seatUsage.endDt.loe(now))
                .fetch()
                ;
    }

    @Override
    public void deleteExpiredSeatUsages(LocalDateTime now) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        query
                .delete(seatUsage)
                .where(seatUsage.endDt.loe(now))
                .execute();
    }

    @Override
    public SeatAvailability getSeatAvailability(Integer roomId) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        NumberExpression<Integer> unusedSeats = new CaseBuilder().when(seatUsage.id.isNull().and(seat.active.isTrue())).then(1).otherwise(0);
        NumberExpression<Integer> usedSeats = new CaseBuilder().when(seatUsage.id.isNotNull()).then(1).otherwise(0);
        NumberExpression<Integer> limitedSeats = new CaseBuilder().when(seat.active.isFalse()).then(1).otherwise(0);

        return query
                .select(Projections.bean(
                        SeatAvailability.class,
                        unusedSeats.sum().as("unusedSeats"),
                        usedSeats.sum().as("usedSeats"),
                        limitedSeats.sum().as("limitedSeats")

                ))
                .from(seat)
                .leftJoin(seatUsage).on(seatUsage.seat.id.eq(seat.id))
                .where(seat.room.id.eq(roomId))
                .groupBy(seat.room.id)
                .fetchOne()
                ;
    }

    @Override
    public Long getCountAvailableSeat() {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .select(seat.id.count())
                .from(seat)
                .join(room).on(room.id.eq(seat.room.id))
                .leftJoin(seatUsage).on(seatUsage.seat.id.eq(seat.id))
                .where(
                        seat.active.isTrue(),
                        seatUsage.id.isNull()
                )
                .fetchOne()
                ;
    }

}
