package com.lycodeing.security.interceptor;

import com.lycodeing.security.context.SecurityContextHolder;
import com.lycodeing.security.entity.SecurityUserDetail;
import com.lycodeing.security.properties.AuthorizationProperties;
import com.lycodeing.security.provider.Authentication;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {


    private final AuthorizationProperties properties;

    public SessionInterceptor(AuthorizationProperties properties) {
        this.properties = properties;
    }

    @Override
    public boolean preHandle(@Nullable HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Object handler) {
        if (properties.isSession() && isIntercept(request)) {
            HttpSession session = request.getSession();
            Authentication authentication = (Authentication) session.getAttribute("authentication");
            log.info("session authentication {}", authentication);
            // 解析 token 并注册到安全上下文
            SecurityUserDetail context = (SecurityUserDetail) authentication.getPrincipal();
            SecurityContextHolder.setContext(context);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        SecurityContextHolder.clearContext();
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 判断是否要拦截
     */
    private boolean isIntercept(HttpServletRequest request) {
        String uri = request.getRequestURI();
        for (String permitUrl : properties.getPermitUrls()) {
            if (uri.startsWith(permitUrl)) {
                return true;
            }
        }
        return false;
    }
}
