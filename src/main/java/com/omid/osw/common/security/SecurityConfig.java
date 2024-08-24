package com.omid.osw.common.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        http
//            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/loginPage", "/auth/joinPage").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/img/**", "/media/**", "/webfonts/**", "/favicon.ico").permitAll()
                .anyRequest().authenticated()
            )
            .requestCache(request-> request.requestCache(requestCache))
            .formLogin(form -> form
                    .loginPage("/auth/loginPage")        // 사용자 정의 로그인 페이지 URL
                    .loginProcessingUrl("/auth/login") // 로그인 처리 URL
                    .defaultSuccessUrl("/",true)     // 로그인 성공 시 이동 url
                    .usernameParameter("userId") // 로그인 시 사용할 파라미터 이름
                    .passwordParameter("userPassword") // 사용자 정의 파라미터 이름 설정
                    .failureHandler(customAuthenticationFailureHandler()) // 로그인 실패 시 로그인 실패 url 커스텀
                    .permitAll()
            )
            .sessionManagement(session -> session
                    .invalidSessionUrl("/auth/loginPage")
                    .maximumSessions(1)
                    .expiredUrl("/auth/loginPage")
                    .maxSessionsPreventsLogin(true)
            )
            .logout(logout -> logout
                    .addLogoutHandler(logoutHandler())
                    .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                    .logoutSuccessUrl("/") // 로그아웃 성공 시 이동 url
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
            )
            .exceptionHandling(exception -> exception
                    .authenticationEntryPoint(new GrpAuthenticationEntryPoint()) // 인증되지 않은 사용자의 리소스 접근 시 수행되는 핸들러
            );

        return http.build();
    }

    /**
     * 로그아웃 중간 핸들러
     * @return
     */
    @Bean
    public LogoutHandler logoutHandler() {
        return new CustomLogoutHandler();
    }

    /**
     * 암호화
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 로그인 오류 시 각 url 지정
     * @return
     */
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}
