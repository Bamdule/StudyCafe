package com.bamdule.studycafe.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true, updatable = false)
    private String phone;

    @Column(updatable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private String password;

    @Column(name = "join_dt", updatable = false)
    private LocalDateTime joinDt;


}
