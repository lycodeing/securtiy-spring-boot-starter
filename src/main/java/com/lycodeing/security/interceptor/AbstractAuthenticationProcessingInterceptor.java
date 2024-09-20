package com.lycodeing.security.interceptor;


import com.lycodeing.security.exception.AuthenticationException;
import com.lycodeing.security.handler.AuthenticationFailureHandler;
import com.lycodeing.security.handler.AuthenticationSuccessHandler;
import com.lycodeing.security.manager.AuthenticationManager;
import com.lycodeing.security.provider.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Setter
public abstract class AbstractAuthenticationProcessingInterceptor implements HandlerInterceptor {

    private String loginUrl = "/login";

    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private AuthenticationFailureHandler authenticationFailureHandler;

    public AuthenticationManager authenticationManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getRequestURI().equals(loginUrl)) {
            return true;
        }
        try {
            Authentication authentication = attemptAuthentication(request, response);
            if (authenticationSuccessHandler != null && authentication != null && authentication.isAuthenticated()) {
                authenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
            }
        } catch (AuthenticationException e) {
            if (authenticationFailureHandler != null) {
                authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            }
            return false;
        }
        return true;
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
