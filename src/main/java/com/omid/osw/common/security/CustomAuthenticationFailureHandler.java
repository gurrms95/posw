package com.omid.osw.common.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 사용자 로그인 시 fail 될 경우 조치 (커스텀)
 */
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 계정 비활성일 때 처리
        if (exception.getMessage().equals("User is disabled")) {
            response.sendRedirect("/auth/login/accessDenied");
        } else {
            // 기타 인증 실패 시 기본 처리
            response.sendRedirect("/auth/login/error");
        }
    }
    
}
