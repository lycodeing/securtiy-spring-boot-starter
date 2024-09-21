package com.lycodeing.security.interceptor;

import com.lycodeing.security.context.SecurityContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Setter
public abstract class AbstractAuthorizationValidationInterceptor  implements HandlerInterceptor {
    /**
     * 跳过认证的url
     */
    protected String[] permitUrls;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!isIntercept(request)){
            // abstract 的认证处理器
            attemptAuthentication(request, response);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理认证信息
        SecurityContextHolder.clearContext();
    }

    public abstract void attemptAuthentication(HttpServletRequest request, HttpServletResponse response);


    /**
     * 判断当前请求是否在跳过认证的范围内
     */
    private boolean isIntercept(HttpServletRequest request) {
       return Arrays.stream(permitUrls).anyMatch(url -> url.equals(request.getRequestURI()));
    }
}
