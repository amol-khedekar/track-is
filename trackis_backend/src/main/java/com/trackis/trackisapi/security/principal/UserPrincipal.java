package com.trackis.trackisapi.security.principal;

import com.trackis.trackisapi.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record UserPrincipal(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // ROLE_ prefix is required
        return user.getRolesView().stream().
                map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name())).
                toList();
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !user.getAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.getAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.getCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getAccountEnabled();
    }
}
