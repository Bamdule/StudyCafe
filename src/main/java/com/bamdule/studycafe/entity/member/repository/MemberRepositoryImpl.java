package com.bamdule.studycafe.entity.member.repository;

import com.bamdule.studycafe.entity.member.PaidMemberVO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static com.bamdule.studycafe.entity.member.QMember.member;
import static com.bamdule.studycafe.entity.product.QProduct.product;
import static com.bamdule.studycafe.entity.product.payment.QProductPayment.productPayment;

public class MemberRepositoryImpl implements MemberRepositoryCustom {

    @Autowired
    private EntityManager em;


    @Override
    public PaidMemberVO findPaidMemberById(Integer id) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        return query
                .select(Projections.bean(
                        PaidMemberVO.class,
                        member.id.as("memberId"),
                        member.name.as("memberName"),
                        productPayment.remainingTime,
                        productPayment.paymentDt,
                        product.name.as("productName"),
                        product.validDays,
                        new CaseBuilder()
                                .when(productPayment.id.isNotNull()).then(true)
                                .otherwise(false).as("payment")
                ))
                .from(member)
                .leftJoin(productPayment).on(productPayment.member.id.eq(member.id))
                .leftJoin(productPayment.product, product)
                .where(member.id.eq(id))
                .fetchOne()
                ;
    }
}
