package com.bamdule.studycafe.entity.studycafe.repository;

import com.bamdule.studycafe.entity.room.RoomVO;
import com.bamdule.studycafe.entity.seat.Seat;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.bamdule.studycafe.entity.seatusage.SeatStatus;
import com.bamdule.studycafe.entity.studycafe.StudyCafeVO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bamdule.studycafe.entity.member.QMember.member;
import static com.bamdule.studycafe.entity.room.QRoom.room;
import static com.bamdule.studycafe.entity.seat.QSeat.seat;
import static com.bamdule.studycafe.entity.seatusage.QSeatUsage.seatUsage;
import static com.bamdule.studycafe.entity.studycafe.QStudyCafe.studyCafe;

public class StudyCafeRepositoryImpl implements StudyCafeRepositoryCustom {

    @Autowired
    private EntityManager em;

//    @Override
//    public List<StudyCafeVO> findAllStudyCafe() {
//        JPAQueryFactory query = new JPAQueryFactory(em);
//        return query.select(Projections.bean(
//                StudyCafeVO.class,
//                studyCafe.id,
//                studyCafe.name,
//                studyCafe.address))
//                .from(studyCafe)
//                .fetch()
//                ;
//
//    }

    @Override
    public List<RoomVO> findAllRoom(Integer studyCafeId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
                .select(Projections.bean(
                        RoomVO.class,
                        room.id,
                        room.name,
                        room.width,
                        room.height))
                .from(room)
                .where(room.studyCafe.id.eq(studyCafeId))
                .fetch()
                ;
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

//    @Override
//    public SeatVO checkAvailableSeat(Integer roomId, Integer seatId) {
//        JPAQueryFactory query = new JPAQueryFactory(em);
//
//        return query
//                .select(Projections.bean(
//                        SeatVO.class,
//                        seat.id,
//                        seat.number
//                ))
//                .from(room)
//                .join(seat).on(seat.room.id.eq(roomId))
//                .leftJoin(seatUsage).on(seatUsage.seat.id.eq(seat.id))
//                .where(
//                        room.id.eq(roomId),
//                        seat.id.eq(seatId),
//                        seatUsage.member.id.isNull()
//                )
//                .fetchOne()
//                ;
//    }
}
