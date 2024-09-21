package com.lycodeing.security.token;

import com.lycodeing.security.core.Authentication;
import com.lycodeing.security.entity.SecurityUser;
import lombok.Setter;

import java.util.Collection;

@Setter
public abstract class AbstractAuthenticationToken implements Authentication {

    private SecurityUser principal;

    private Collection<String> authorities;

    private boolean authenticated = false;

    public AbstractAuthenticationToken(Collection<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<String> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }
}
