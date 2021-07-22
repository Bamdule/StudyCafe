package com.bamdule.studycafe.entity.seat.repository;

import com.bamdule.studycafe.entity.seat.QSeat;
import com.bamdule.studycafe.entity.seat.Seat;
import com.bamdule.studycafe.entity.seat.SeatVO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.bamdule.studycafe.entity.seat.QSeat.seat;

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
}
