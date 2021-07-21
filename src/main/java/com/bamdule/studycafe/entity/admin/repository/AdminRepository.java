package com.bamdule.studycafe.entity.admin.repository;

import com.bamdule.studycafe.entity.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findAdminByAccount(String account);
}
