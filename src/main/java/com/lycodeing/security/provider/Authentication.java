package com.lycodeing.security.provider;

import com.lycodeing.security.entity.SecurityUser;

public interface Authentication extends SecurityUser {


    Object getPrincipal();  // 获取主体（如用户名或用户详细信息）

    boolean isAuthenticated();  // 是否已认证

    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;  // 设置认证状态
}
