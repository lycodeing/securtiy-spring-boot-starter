package com.lycodeing.security.manager;

import com.lycodeing.security.core.Authentication;
import com.lycodeing.security.core.AuthenticationProvider;
import com.lycodeing.security.exception.AuthenticationException;

import java.util.ArrayList;
import java.util.List;


public class AuthenticationManager {
    private final List<AuthenticationProvider> authenticationProvider = new ArrayList<>();

    public Authentication authenticate(Authentication authentication) {
        for (AuthenticationProvider provider : this.authenticationProvider) {
            if (provider.supports(authentication.getClass())) {
                return provider.authenticate(authentication);
            }
        }
        throw new AuthenticationException("No AuthenticationProvider found for " + authentication.getClass().getName());
    }


    public synchronized void addProvider(AuthenticationProvider provider) {
        this.authenticationProvider.add(provider);
    }
}
