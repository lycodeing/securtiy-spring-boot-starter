package com.lycodeing.security.manager;

import com.lycodeing.security.exception.AuthenticationException;
import com.lycodeing.security.provider.Authentication;
import com.lycodeing.security.provider.AuthenticationProvider;
import lombok.Setter;

import java.util.List;

@Setter
public class AuthenticationManager {
    private List<AuthenticationProvider> authenticationProvider;

    public Authentication authenticate(Authentication authentication) {
        for (AuthenticationProvider provider : authenticationProvider) {
            if (provider.supports(authentication.getClass())) {
                return provider.authenticate(authentication);
            }
        }
        throw new AuthenticationException("No AuthenticationProvider found for " + authentication.getClass().getName());
    }

}
