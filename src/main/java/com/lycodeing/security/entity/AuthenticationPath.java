package com.lycodeing.security.entity;

import lombok.Getter;

import java.util.Set;
@Getter
public class AuthenticationPath {

    private final String path; // 登录路径
    private final Set<String> allowedMethods; // 允许的请求方法

    public AuthenticationPath(String path, Set<String> allowedMethods) {
        this.path = path;
        this.allowedMethods = allowedMethods;
    }
}
