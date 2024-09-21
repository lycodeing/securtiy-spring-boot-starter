package com.lycodeing.security.handler;

import com.lycodeing.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationSuccessHandler {

    void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                 Authentication authentication);
}
