package com.project.officequiz.security.usermanagement;

import com.project.officequiz.entity.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

public class SecurityUser implements UserDetails {
    private final Users users;

    public SecurityUser(Users users) {
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.users.getAuthorities().stream().map(SecurityAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return this.users.getPassword();
    }

    @Override
    public String getUsername() {
        return this.users.getUserName();
    }

    @Override
    public boolean isEnabled() {
        return users.isActive();
    }
}
