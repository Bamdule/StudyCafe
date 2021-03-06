package com.bamdule.studycafe.entity.product.payment;

import com.bamdule.studycafe.entity.member.Member;
import com.bamdule.studycafe.entity.product.Product;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "product_payment")
public class ProductPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "remaining_time", columnDefinition = "int default 0")
    private Integer remainingTime;

    @Column(name = "payment_dt")
    private LocalDateTime paymentDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    private Member member;

}
