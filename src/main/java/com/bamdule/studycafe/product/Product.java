package com.bamdule.studycafe.product;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    @Column(columnDefinition = "int default 0")
    private int hour;

    @Column(name = "valid_days", columnDefinition = "int default 0")
    private int validDays;

    @Column(columnDefinition = "int default 0")
    private int price;

}
