package com.lycodeing.security.core;

public interface AuthenticationProvider {

    Authentication authenticate(Authentication authentication);


    default boolean supports(Class<?> authentication) {
        return false;
    }
}
