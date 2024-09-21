package com.lycodeing.security.config;

import com.lycodeing.security.entity.AuthenticationPath;
import com.lycodeing.security.handler.AuthenticationFailureHandler;
import com.lycodeing.security.handler.AuthenticationSuccessHandler;
import com.lycodeing.security.interceptor.AbstractAuthenticationProcessingInterceptor;
import com.lycodeing.security.interceptor.AbstractAuthorizationValidationInterceptor;
import com.lycodeing.security.manager.AuthenticationManager;
import com.lycodeing.security.properties.AuthorizationProperties;
import jakarta.annotation.Nonnull;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;
import java.util.Set;

public class InterceptorRegistryConfig implements WebMvcConfigurer {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final AuthenticationManager authenticationManager;

    private final ApplicationContext applicationContext;

    private final AuthorizationProperties properties;

    public InterceptorRegistryConfig(AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationFailureHandler authenticationFailureHandler, AuthenticationManager authenticationManager, ApplicationContext applicationContext, AuthorizationProperties properties) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationManager = authenticationManager;
        this.applicationContext = applicationContext;
        this.properties = properties;
    }


    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        // 使用Lambda表达式提高代码可读性
        Map<String, AbstractAuthenticationProcessingInterceptor> applicationContextBeansOfType = applicationContext.getBeansOfType(AbstractAuthenticationProcessingInterceptor.class);
        for (Map.Entry<String, AbstractAuthenticationProcessingInterceptor> stringAbstractAuthenticationProcessingInterceptorEntry : applicationContextBeansOfType.entrySet()) {
            AbstractAuthenticationProcessingInterceptor value = getAbstractAuthenticationProcessingInterceptor(stringAbstractAuthenticationProcessingInterceptorEntry);
            registry.addInterceptor(value).addPathPatterns("/**");
        }
        Map<String, AbstractAuthorizationValidationInterceptor> authorizationValidationInterceptorMap = applicationContext.getBeansOfType(AbstractAuthorizationValidationInterceptor.class);
        for (Map.Entry<String, AbstractAuthorizationValidationInterceptor> stringAbstractAuthorizationValidationInterceptorEntry : authorizationValidationInterceptorMap.entrySet()) {
            AbstractAuthorizationValidationInterceptor value = stringAbstractAuthorizationValidationInterceptorEntry.getValue();
            value.setPermitUrls(properties.getPermitUrls());
            registry.addInterceptor(value).addPathPatterns("/**");
        }

    }

    private AbstractAuthenticationProcessingInterceptor getAbstractAuthenticationProcessingInterceptor(Map.Entry<String, AbstractAuthenticationProcessingInterceptor> stringAbstractAuthenticationProcessingInterceptorEntry) {
        AbstractAuthenticationProcessingInterceptor value = stringAbstractAuthenticationProcessingInterceptorEntry.getValue();
        value.setAuthenticationManager(authenticationManager);
        value.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        value.setAuthenticationFailureHandler(authenticationFailureHandler);
        value.setSession(properties.isSession());
        value.setAuthenticationPath(new AuthenticationPath(properties.getLoginUrl(), Set.of("POST")));
        return value;
    }
}
