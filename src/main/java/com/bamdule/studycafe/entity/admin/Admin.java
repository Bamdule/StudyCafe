package com.bamdule.studycafe.entity.admin;

import com.bamdule.studycafe.entity.admin.role.AdminRole;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_role_id")
    private AdminRole adminRole;
}
