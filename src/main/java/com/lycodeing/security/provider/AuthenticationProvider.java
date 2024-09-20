package com.lycodeing.security.provider;

public interface AuthenticationProvider {

    Authentication authenticate(Authentication authentication);


    default boolean supports(Class<?> authentication) {
        return false;
    }
}
