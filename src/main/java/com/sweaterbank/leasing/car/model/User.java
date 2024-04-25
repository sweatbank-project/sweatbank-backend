package com.sweaterbank.leasing.car.model;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.GrantedAuthority;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class User implements UserDetails {

    private final String id;
    private final String username;
    private final String phoneNumber;
    private final String personalId;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final Date birthdate;
    private final String address;
    private final Roles role;
    private final Timestamp accountExpirationDate;
    private final boolean accountLocked;
    private final boolean enabled;

    public User(String id, String username, String phoneNumber, String personalId, String password, String firstName, String lastName, Date birthdate, String address, Roles role, Timestamp accountExpirationDate, boolean accountLocked, boolean enabled) {
        this.id = id;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.personalId = personalId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.address = address;
        this.role = role;
        this.accountExpirationDate = accountExpirationDate;
        this.accountLocked = accountLocked;
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();

        if (role != null) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
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
                new Timestamp(LocalDateTime.now().getSecond()).before(this.accountExpirationDate);
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

