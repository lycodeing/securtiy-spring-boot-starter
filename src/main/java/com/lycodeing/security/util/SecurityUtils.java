package com.lycodeing.security.util;

import com.lycodeing.security.context.SecurityContextHolder;

public class SecurityUtils {

    public static String getUsername() {
        return SecurityContextHolder.getContext().getUsername();
    }

    public static boolean hasRole(String role) {
        return SecurityContextHolder.getContext().getRoles().contains(role);
    }

    public static boolean hasPermission(String permission) {
        return SecurityContextHolder.getContext().getPermissions().contains(permission);
    }
}
