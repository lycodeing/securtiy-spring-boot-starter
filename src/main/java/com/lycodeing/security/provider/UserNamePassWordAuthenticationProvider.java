package com.lycodeing.security.provider;

import com.lycodeing.security.core.Authentication;
import com.lycodeing.security.core.AuthenticationProvider;
import com.lycodeing.security.entity.SecurityUser;
import com.lycodeing.security.exception.AuthenticationException;
import com.lycodeing.security.service.SecurityUserService;
import com.lycodeing.security.token.UsernamePasswordAuthenticationToken;

/**
 * 认证处理器
 */
public class UserNamePassWordAuthenticationProvider implements AuthenticationProvider {
    private final SecurityUserService securityUserService;

    public UserNamePassWordAuthenticationProvider(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        SecurityUser securityUser = securityUserService.loadUserByUsername(token.getUsername());
        if (securityUser != null && token.getPassword().equals(securityUser.getPassword())) {
            return new UsernamePasswordAuthenticationToken(securityUser, securityUser.getAuthorities());
        }
        throw new AuthenticationException("用户名或密码错误");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
