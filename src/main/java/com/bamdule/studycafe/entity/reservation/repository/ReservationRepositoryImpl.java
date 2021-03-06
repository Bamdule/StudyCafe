package com.bamdule.studycafe.entity.reservation.repository;

import com.bamdule.studycafe.entity.reservation.QReservation;
import com.bamdule.studycafe.entity.reservation.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.bamdule.studycafe.entity.reservation.QReservation.reservation;

public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    @Autowired
    private EntityManager em;

//    @Override
//    public Optional<Reservation> findFirstReservation() {
//        JPAQueryFactory query = new JPAQueryFactory(em);
//
//        Reservation reservation = query
//                .select(QReservation.reservation)
//                .from(QReservation.reservation)
//                .fetchFirst();
//
//        return Optional.ofNullable(reservation);
//    }
    @Override
    public List<Reservation> getListReservationByLimit(Long limit) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .select(reservation)
                .from(reservation)
                .limit(limit)
                .orderBy(reservation.id.asc())
                .fetch();

    }
    @Override
    public Long getCountReservation() {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .select(reservation.count())
                .from(reservation)
                .fetchOne()
                ;
    }
}
