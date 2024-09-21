package com.lycodeing.security.core;

import java.util.Collection;

public interface Authentication  {
    Collection<String> getAuthorities();

    Object getPrincipal();  // 获取主体（如用户名或用户详细信息）

    boolean isAuthenticated();  // 是否已认证

    void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException;  // 设置认证状态
}
