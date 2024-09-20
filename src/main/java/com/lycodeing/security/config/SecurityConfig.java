package com.lycodeing.security.config;

import com.lycodeing.security.handler.AuthenticationFailureHandler;
import com.lycodeing.security.handler.AuthenticationSuccessHandler;
import com.lycodeing.security.interceptor.AbstractAuthenticationProcessingInterceptor;
import com.lycodeing.security.interceptor.SessionInterceptor;
import com.lycodeing.security.interceptor.UserNamePassWordInterceptor;
import com.lycodeing.security.manager.AuthenticationManager;
import com.lycodeing.security.properties.AuthorizationProperties;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(AuthorizationProperties.class)
public class SecurityConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    private final AuthorizationProperties properties;


    public SecurityConfig(ApplicationContext applicationContext, AuthorizationProperties properties) {
        this.applicationContext = applicationContext;
        this.properties = properties;
    }

    @Override
    public void addInterceptors(@Nonnull InterceptorRegistry registry) {
        String[] beanNames = applicationContext.getBeanNamesForType(AbstractAuthenticationProcessingInterceptor.class);
        for (String beanName : beanNames) {
            AbstractAuthenticationProcessingInterceptor interceptor = applicationContext.getBean(beanName, AbstractAuthenticationProcessingInterceptor.class);
            registry.addInterceptor(interceptor).addPathPatterns("/**");
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        return new AuthenticationManager();
    }


    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
       return (request, response, authentication) -> request.getSession().setAttribute("authentication",authentication);
    }


    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return (request, response, exception) -> log.info("认证失败");
    }



    @Bean
    public SessionInterceptor tokenInterceptor(){
        return new SessionInterceptor(properties);
    }

    @Bean
    public UserNamePassWordInterceptor loginInterceptor(){
        UserNamePassWordInterceptor userNamePassWordInterceptor = new UserNamePassWordInterceptor();
        userNamePassWordInterceptor.setLoginUrl(properties.getLoginUrl());
        userNamePassWordInterceptor.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        userNamePassWordInterceptor.setAuthenticationFailureHandler(authenticationFailureHandler());
        userNamePassWordInterceptor.setAuthenticationManager(authenticationManager());
        return userNamePassWordInterceptor;
    }


}
