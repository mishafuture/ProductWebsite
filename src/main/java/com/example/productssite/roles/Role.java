package com.example.productssite.roles;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority{
    ADMIN_ROLE, USER_ROLE;

    @Override
    public String getAuthority() {
        return name();
    }
}
