package com.bamdule.studycafe.entity.seatusage.repository;

import com.bamdule.studycafe.entity.member.QMember;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seatusage.SeatUsageVO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.bamdule.studycafe.entity.member.QMember.member;
import static com.bamdule.studycafe.entity.seat.QSeat.seat;
import static com.bamdule.studycafe.entity.seatusage.QSeatUsage.seatUsage;

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
                        seatUsage.seat.status,
                        seatUsage.member.id.as("memberId"),
                        seatUsage.member.name.as("memberName"),
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
                        seat.number,
                        seat.status
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
                        seat.number,
                        seat.status
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
                .where(seatUsage.endDt.goe(now))
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
}