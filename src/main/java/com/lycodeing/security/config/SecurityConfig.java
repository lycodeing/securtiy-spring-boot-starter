package com.lycodeing.security.config;

import com.lycodeing.security.entity.SecurityUserDetail;
import com.lycodeing.security.handler.AuthenticationFailureHandler;
import com.lycodeing.security.handler.AuthenticationSuccessHandler;
import com.lycodeing.security.interceptor.SessionInterceptor;
import com.lycodeing.security.interceptor.UserNamePassWordInterceptor;
import com.lycodeing.security.manager.AuthenticationManager;
import com.lycodeing.security.properties.AuthorizationProperties;
import com.lycodeing.security.provider.UserNamePassWordAuthenticationProvider;
import com.lycodeing.security.service.SecurityUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Slf4j
@EnableConfigurationProperties(AuthorizationProperties.class)
public class SecurityConfig {

    private final ApplicationContext applicationContext;
    private final AuthorizationProperties properties;

    @Autowired
    public SecurityConfig(ApplicationContext applicationContext, AuthorizationProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    @Bean
    public InterceptorRegistryConfig interceptorRegistryConfig(AuthenticationSuccessHandler authenticationSuccessHandler,
                                                               AuthenticationFailureHandler authenticationFailureHandler,
                                                               AuthenticationManager authenticationManager,
                                                               ApplicationContext applicationContext,
                                                               AuthorizationProperties properties) {
        return new InterceptorRegistryConfig(authenticationSuccessHandler, authenticationFailureHandler, authenticationManager, applicationContext, properties);
    }


    /**
     * 默认实现
     */
    @Bean
    @ConditionalOnMissingBean
    public SecurityUserService securityUserService(){
        return identifier -> new SecurityUserDetail("admin", "123456", "salt", List.of("ROLE_ADMIN"), List.of("system"));
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationManager authenticationManager() {
        // 使用构造函数注入依赖，避免硬编码
        AuthenticationManager authenticationManager = new AuthenticationManager();
        authenticationManager.addProvider(new UserNamePassWordAuthenticationProvider(applicationContext.getBean(SecurityUserService.class)));
        return authenticationManager;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) ->
                request.getSession().setAttribute("authentication", authentication);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> log.info("认证失败: {}", exception.getMessage());
    }

    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor(properties);
    }

    @Bean
    public UserNamePassWordInterceptor loginInterceptor() {
        UserNamePassWordInterceptor interceptor = new UserNamePassWordInterceptor();
        // 使用setter简化属性设置
        interceptor.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        interceptor.setAuthenticationFailureHandler(authenticationFailureHandler());
        interceptor.setAuthenticationManager(authenticationManager());
        return interceptor;
    }
}