package com.lycodeing.security.entity;

import lombok.Getter;

import java.util.Collection;

@Getter
public class SecurityUserDetail implements SecurityUser {
    private final String username;
    private final String password;
    private final String salt;
    private final Collection<String> roles;
    private final Collection<String> permissions;

    public SecurityUserDetail(String username, String password, String salt, Collection<String> roles, Collection<String> permissions) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.roles = roles;
        this.permissions = permissions;
    }

    @Override
    public Collection<String> getAuthorities() {
        return permissions;
    }
}
