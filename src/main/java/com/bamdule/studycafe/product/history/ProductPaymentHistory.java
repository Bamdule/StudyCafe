package com.bamdule.studycafe.product.history;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ProductPaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id", nullable = false)
    private Integer productId;

    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name = "start_dt", nullable = false)
    private LocalDateTime startDt;

    @Column(name = "end_dt", nullable = false)
    private LocalDateTime endDt;

}
