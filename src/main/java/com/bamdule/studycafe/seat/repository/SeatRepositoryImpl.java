package com.bamdule.studycafe.seat.repository;

import com.bamdule.studycafe.seat.SeatVO;
import com.bamdule.studycafe.seat.seatusage.QSeatUsage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bamdule.studycafe.member.QMember.member;
import static com.bamdule.studycafe.room.QRoom.room;
import static com.bamdule.studycafe.seat.QSeat.seat;
import static com.bamdule.studycafe.seat.seatusage.QSeatUsage.seatUsage;

public class SeatRepositoryImpl implements SeatRepositoryCustom {

    @Autowired
    private EntityManager em;

    @Override
    public List<SeatVO> findAllSeatByRoomId(Integer roomId) {
        return null;
//        JPAQueryFactory query = new JPAQueryFactory(em);
//
//        return query
//                .select(Projections.bean(
//                        SeatVO.class,
//                        seat.id,
//                        seat.number,
//                        member.id.as("memberId"),
//                        member.name.as("memberName"),
//                        seatUsage.startDt,
//                        seat.status
//                ))
//                .from(seat)
//                .join(seat.room, room)
//                .leftJoin(seatUsage).on(seatUsage.seat.id.eq(seat.id))
//                .leftJoin(seatUsage.member, member)
//                .where(seat.room.id.eq(roomId))
//                .orderBy(seat.number.asc())
//                .fetch()
//                ;

    }
}
