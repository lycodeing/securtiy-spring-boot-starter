package com.lycodeing.security.entity;

import java.util.Collection;

public interface SecurityUser {
    String getUsername();

    String getPassword();

    Collection<String> getAuthorities();
}
