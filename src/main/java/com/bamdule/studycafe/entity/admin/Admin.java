package com.bamdule.studycafe.entity.admin;

import com.bamdule.studycafe.entity.admin.role.AdminRole;
import com.bamdule.studycafe.entity.studycafe.StudyCafe;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String account;

    private String password;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "admin_role_id")
    private AdminRole adminRole;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studycafe_id", nullable = false)
    private StudyCafe studyCafe;
}
