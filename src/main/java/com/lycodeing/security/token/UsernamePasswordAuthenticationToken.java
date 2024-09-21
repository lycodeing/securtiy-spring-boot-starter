package com.lycodeing.security.token;

import com.lycodeing.security.entity.SecurityUser;
import lombok.Getter;

import java.util.Collection;

@Getter
public class UsernamePasswordAuthenticationToken extends AbstractAuthenticationToken {
    private String username;
    private String password;

    private SecurityUser principal;
    private Collection<String> authorities;


    public UsernamePasswordAuthenticationToken(String username, String password) {
        super(null);
        this.username = username;
        this.password = password;
        this.setAuthenticated(false);
    }

    public UsernamePasswordAuthenticationToken(SecurityUser principal, Collection<String> authorities) {
        super(authorities);
        this.principal = principal;
        this.authorities = authorities;
        this.setAuthenticated(true);
    }

    @Override
    public Collection<String> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
