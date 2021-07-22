package com.bamdule.studycafe.security;

import com.bamdule.studycafe.entity.studycafe.StudyCafeVO;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author MW
 */
@Builder
public class AdminDetails implements UserDetails, Serializable {

    private Integer id;
    private String username;
    private String password;

    private Boolean enabled;

    private StudyCafeVO studyCafe;


    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public StudyCafeVO getStudyCafe() {
        return studyCafe;
    }

    public void setStudyCafe(StudyCafeVO studyCafe) {
        this.studyCafe = studyCafe;
    }
}
