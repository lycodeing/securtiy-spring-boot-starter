package com.lycodeing.security.provider;

import com.lycodeing.security.entity.SecurityUser;
import com.lycodeing.security.exception.AuthenticationException;
import com.lycodeing.security.service.SecurityUserService;
import com.lycodeing.security.token.UsernamePasswordAuthenticationToken;

/**
 * 认证处理器
 */
public class UserNamePassWordAuthenticationProvider implements AuthenticationProvider{
   private final SecurityUserService securityUserService;

    public UserNamePassWordAuthenticationProvider(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getUsername();
        String password = authentication.getPassword();
        SecurityUser securityUser = securityUserService.loadUserByUsername(username);
        if (securityUser != null && securityUser.getPassword().equals(password)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            usernamePasswordAuthenticationToken.setAuthenticated(true);
            usernamePasswordAuthenticationToken.setAuthorities(securityUser.getAuthorities());
            usernamePasswordAuthenticationToken.setPrincipal(securityUser);
            return usernamePasswordAuthenticationToken;
        }
        throw new AuthenticationException("用户名或密码错误");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
