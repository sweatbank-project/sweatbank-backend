package com.sweaterbank.leasing.car.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class User implements UserDetails {

    private final String id;
    private final String personalDataId;
    private final String username;
    private final String password;
    private final String role;
    private final LocalDateTime accountExpirationDate;
    private final boolean accountLocked;
    private final boolean enabled;

    public User(String id, String personalDataId, String username, String password, String role, Timestamp accountExpirationDate, boolean accountLocked, boolean enabled) {
        this.id = id;
        this.personalDataId = personalDataId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.accountExpirationDate = accountExpirationDate.toLocalDateTime();
        this.accountLocked = accountLocked;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (role != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role)); // Assuming role names are prefixed with "ROLE_"
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountExpirationDate == null ||
                LocalDateTime.now().isBefore(this.accountExpirationDate);
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
