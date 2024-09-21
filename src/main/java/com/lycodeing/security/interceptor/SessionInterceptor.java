package com.lycodeing.security.interceptor;

import com.lycodeing.security.context.SecurityContextHolder;
import com.lycodeing.security.core.Authentication;
import com.lycodeing.security.entity.SecurityUserDetail;
import com.lycodeing.security.properties.AuthorizationProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionInterceptor extends AbstractAuthorizationValidationInterceptor {


    private final AuthorizationProperties properties;

    public SessionInterceptor(AuthorizationProperties properties) {
        this.properties = properties;
    }

    @Override
    public void attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        if (properties.isSession()) {
            HttpSession session = request.getSession();
            Authentication authentication = (Authentication) session.getAttribute("authentication");
            log.info("session authentication {}", authentication);
            // 解析 token 并注册到安全上下文
            SecurityUserDetail context = (SecurityUserDetail) authentication.getPrincipal();
            SecurityContextHolder.setContext(context);

        }
    }
}
