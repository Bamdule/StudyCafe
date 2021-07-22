/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bamdule.studycafe.security;

import com.bamdule.studycafe.entity.admin.Admin;
import com.bamdule.studycafe.entity.admin.repository.AdminRepository;
import com.bamdule.studycafe.entity.studycafe.StudyCafeVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author MW
 */
@Component
public class AdminProvider implements AuthenticationProvider {

    @Autowired
    private AdminRepository adminRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Authentication authenticate(Authentication a) throws AuthenticationException {
        String account = a.getName();
        String password = a.getCredentials().toString();

        logger.debug("[MYTEST] account : {}, password : {}", account, password);

        Optional<Admin> optionalAdmin = adminRepository.findAdminByAccount(account);

        if (optionalAdmin.isEmpty()) {
            throw new BadCredentialsException("존재하지 않는 계정입니다.");
        }
        Admin admin = optionalAdmin.get();

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            throw new BadCredentialsException("패스워드가 일치하지 않습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (admin.getAdminRole() != null) {
            authorities.add(new SimpleGrantedAuthority(admin.getAdminRole().getName()));
        }

        StudyCafeVO studyCafeVO = StudyCafeVO.builder()
                .id(admin.getStudyCafe().getId())
                .name(admin.getStudyCafe().getName())
                .address(admin.getStudyCafe().getAddress())
                .build();

        AdminDetails adminDetails = AdminDetails.builder()
                .id(admin.getId())
                .username(account)
                .authorities(authorities)
                .studyCafe(studyCafeVO)
                .build();
        
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(adminDetails, passwordEncoder.encode(password), authorities);

        return token;

    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
}
