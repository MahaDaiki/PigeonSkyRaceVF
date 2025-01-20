package com.example.pigeon.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_ORGANIZER;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
