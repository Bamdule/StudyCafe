package com.bamdule.studycafe.entity.seat.repository;

import com.bamdule.studycafe.entity.seat.QSeat;
import com.bamdule.studycafe.entity.seat.Seat;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seatusage.SeatStatus;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.bamdule.studycafe.entity.member.QMember.member;
import static com.bamdule.studycafe.entity.room.QRoom.room;
import static com.bamdule.studycafe.entity.seat.QSeat.seat;
import static com.bamdule.studycafe.entity.seatusage.QSeatUsage.seatUsage;

public class SeatRepositoryImpl implements SeatRepositoryCustom {

    @Autowired
    private EntityManager em;

    @Override
    public Optional<Seat> findSeat(Integer roomId, Integer seatId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        Seat findSeat = query
                .select(QSeat.seat)
                .from(QSeat.seat)
                .where(
                        QSeat.seat.room.id.eq(roomId),
                        QSeat.seat.id.eq(seatId)
                )
                .fetchOne();


        return Optional.ofNullable(findSeat);
    }

    @Override
    public List<SeatVO> findAllSeat(Integer roomId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
                .select(Projections.bean(
                        SeatVO.class,
                        seat.id,
                        seat.number,
                        member.id.as("memberId"),
                        member.name.as("memberName"),
                        seatUsage.startDt,
                        seatUsage.endDt,
                        seat.row,
                        seat.col,
                        new CaseBuilder()
                                .when(seat.active.isFalse()).then(SeatStatus.LIMIT.toString())
                                .when(member.id.isNotNull()).then(SeatStatus.USED.toString())
                                .otherwise(SeatStatus.UNUSED.toString())
                                .as("status")
                ))
                .from(seat)
                .join(seat.room, room)
                .leftJoin(seatUsage).on(seatUsage.seat.id.eq(seat.id))
                .leftJoin(seatUsage.member, member)
                .where(seat.room.id.eq(roomId))
                .orderBy(seat.number.asc())
                .fetch()
                ;
    }
}
