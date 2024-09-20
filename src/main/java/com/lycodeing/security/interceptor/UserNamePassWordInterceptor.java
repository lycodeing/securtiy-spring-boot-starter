package com.lycodeing.security.interceptor;

import com.lycodeing.security.provider.Authentication;
import com.lycodeing.security.token.UsernamePasswordAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;


@Setter
public class UserNamePassWordInterceptor extends AbstractAuthenticationProcessingInterceptor {

    private String usernameParameter = "username";

    private String passwordParameter = "password";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = getAuthentication(request);
        return super.authenticationManager.authenticate(authentication);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        String username = getUsername(request);
        String password = getPassword(request);
        return new UsernamePasswordAuthenticationToken(username, password);
    }



    private String getUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    private String getPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }


}
