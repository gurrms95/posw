package com.omid.osw.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
public class GrpAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        /**
         * ajax의 경우 http request header에 XMLHttpRequest 라는값이 세팅되어 온다.
         * 인증되지 않은 사용자가 ajax로 리소스를 요청 한 경우 "인증되지 않았다"라는 에러 방생
         * 나머지(ajax 말고 form 등 요청)는 인증을 위해 로그인 페이지로 리다이렉트
         */
        if ("XMLHttpRequest".equals(request.getHeader("x-request-with"))) {
            log.info("인가되지 않은 사용자입니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증되지 않았습니다.");
        } else {
            response.sendRedirect("/auth/loginPage");
        }
    }
}
