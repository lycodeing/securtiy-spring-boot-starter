package com.lycodeing.security.token;

import com.lycodeing.security.entity.SecurityUser;
import com.lycodeing.security.provider.Authentication;
import lombok.Data;

import java.util.Collection;

@Data
public class UsernamePasswordAuthenticationToken implements Authentication {
    private String username;
    private String password;

    private SecurityUser principal;
    private Collection<String> authorities;

    private boolean isAuthenticated;

    public UsernamePasswordAuthenticationToken(String username, String password) {
        this.username = username;
        this.password = password;
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
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }
}
