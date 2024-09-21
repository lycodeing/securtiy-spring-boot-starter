package com.lycodeing.security.interceptor;


import com.lycodeing.security.core.Authentication;
import com.lycodeing.security.entity.AuthenticationPath;
import com.lycodeing.security.exception.AuthenticationException;
import com.lycodeing.security.handler.AuthenticationFailureHandler;
import com.lycodeing.security.handler.AuthenticationSuccessHandler;
import com.lycodeing.security.manager.AuthenticationManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Slf4j
@Setter
public abstract class AbstractAuthenticationProcessingInterceptor implements HandlerInterceptor {

    private AuthenticationPath authenticationPath = new AuthenticationPath("/login", Set.of("POST"));

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private AuthenticationFailureHandler authenticationFailureHandler;

    public AuthenticationManager authenticationManager;


    private boolean isSession;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // 如果路径匹配，但方法不在允许范围内，则返回错误
        if (requestURI.equals(authenticationPath.getPath()) && !isValidMethod(method)) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return false;
        }
        if (requestURI.equals(authenticationPath.getPath()) && isValidMethod(method)) {
            try {
                // 尝试进行认证
                Authentication authentication = attemptAuthentication(request, response);
                if (authentication != null && authentication.isAuthenticated()) {
                    handleSuccess(request, response, authentication);
                } else {
                    handleFailure(request, response, new AuthenticationException("Authentication failed") {
                    });
                }
                return false; // 认证过程结束，阻止后续处理
            } catch (AuthenticationException e) {
                handleFailure(request, response, e);
                return false; // 认证失败，阻止后续处理
            }
        }
        return true;
    }

    private void handleSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception {
        if (isSession) {
            request.getSession().setAttribute("authentication", authentication); // 将认证信息存储在 session 中
        }
        if (authenticationSuccessHandler != null) {
            authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication); // 调用成功处理器
        }
    }

    private void handleFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws Exception {
        if (authenticationFailureHandler != null) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, e); // 调用失败处理器
        } else {
            log.error("Authentication failed: {}", e.getMessage()); // 打印错误日志
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication Failed");
        }
    }

    private boolean isValidRequestURI(String requestURI) {
        // 进行安全性检查
        return requestURI.startsWith("/") && !requestURI.contains("..");
    }

    private boolean isValidMethod(String method) {
        // 进行安全性检查
        return authenticationPath.getAllowedMethods().contains(method.toUpperCase());
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


    public abstract Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response);


}
