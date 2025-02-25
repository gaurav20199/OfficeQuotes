package com.project.officequiz.security.usermanagement;

import com.project.officequiz.entity.Authority;
import org.springframework.security.core.GrantedAuthority;

public class SecurityAuthority implements GrantedAuthority {

    private final Authority authority;
    public SecurityAuthority(Authority authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority.getName();
    }
}
