package com.bamdule.studycafe.entity.seat.repository;

import com.bamdule.studycafe.entity.seat.SeatVO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

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
