package com.lycodeing.security.context;

import com.lycodeing.security.entity.SecurityUserDetail;

public class SecurityContextHolder {
    private static final ThreadLocal<SecurityUserDetail> contextHolder = new ThreadLocal<>();

    public static void setContext(SecurityUserDetail context) {
        contextHolder.set(context);
    }

    public static SecurityUserDetail getContext() {
        return contextHolder.get();
    }

    public static void clearContext() {
        contextHolder.remove();
    }
}
