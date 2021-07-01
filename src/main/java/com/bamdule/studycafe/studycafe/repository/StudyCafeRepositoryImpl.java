package com.bamdule.studycafe.studycafe.repository;

import com.bamdule.studycafe.room.QRoom;
import com.bamdule.studycafe.room.RoomVO;
import com.bamdule.studycafe.seat.SeatVO;
import com.bamdule.studycafe.studycafe.StudyCafeVO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

import static com.bamdule.studycafe.member.QMember.member;
import static com.bamdule.studycafe.room.QRoom.room;
import static com.bamdule.studycafe.seat.QSeat.seat;
import static com.bamdule.studycafe.seat.seatusage.QSeatUsage.seatUsage;
import static com.bamdule.studycafe.studycafe.QStudyCafe.studyCafe;

public class StudyCafeRepositoryImpl implements StudyCafeRepositoryCustom {

    @Autowired
    private EntityManager em;

    @Override
    public List<StudyCafeVO> findAllStudyCafe() {
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query.select(Projections.bean(
                StudyCafeVO.class,
                studyCafe.id,
                studyCafe.name,
                studyCafe.address))
                .from(studyCafe)
                .fetch()
                ;

    }

    @Override
    public List<RoomVO> findAllRoom(Integer studyCafeId) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        return query
                .select(Projections.bean(
                        RoomVO.class,
                        room.id,
                        room.name))
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
                        seat.status
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
