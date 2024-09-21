package com.lycodeing.security.aspect;

import com.lycodeing.security.annotation.PermissionCheck;
import com.lycodeing.security.annotation.RoleCheck;
import com.lycodeing.security.exception.PermissionDeniedException;
import com.lycodeing.security.exception.RoleDeniedException;
import com.lycodeing.security.util.SecurityUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Arrays;

@Aspect
public class SecurityAspect {

    @Before("@annotation(roleCheck)")
    public void checkRole(RoleCheck roleCheck) {
        // 从 ThreadLocal 中获取当前用户的角色
        String[] roles = roleCheck.roles();
        // 检查角色逻辑
        boolean anyMatch = Arrays.stream(roles).anyMatch(SecurityUtils::hasRole);
        if (!anyMatch) {
            throw new RoleDeniedException("Role Denied");
        }
    }

    @Before("@annotation(permissionCheck)")
    public void checkPermission(PermissionCheck permissionCheck) {
        // 从 ThreadLocal 中获取当前用户的权限
        String[] permissions = permissionCheck.permissions();
        // 检查权限逻辑
        boolean anyMatch = Arrays.stream(permissions).anyMatch(SecurityUtils::hasPermission);
        if (!anyMatch) {
            throw new PermissionDeniedException("Permission Denied");
        }
    }
}
