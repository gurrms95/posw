//package com.omid.osw.common.security;
//
//import com.omid.osw.common.security.service.OswUserDetailService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//@EnableMethodSecurity
//public class SecurityConfig_back {
//
//    private final OswUserDetailService oswUserDetailService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(auth -> auth
//                            .requestMatchers("/","/login").permitAll()
//               .anyRequest().permitAll() // 모든 요청 임시 오픈 : 로그인 및 권한 체크 구현 완료시 수정 필요
//                //  .requestMatchers("/auth/login", "/auth/loginPage", "/auth/error", "/auth/logout", "/auth/accessDenied",
//                //          "/member/memberJoin", "/forget", "/email/check",
//                //          "/css/**", "/js/**", "/images/**", "/img/**", "/media/**", "/webfonts/**",
//                //          "/api/**", "/ui/**", "/common/**", "/ajaxTest/**", "/system/**").permitAll()
//                //  .requestMatchers("/admin/**").hasRole("ADMIN")
//                //  .anyRequest().authenticated()
//            )
//            .formLogin(form -> form
//                .loginPage("/auth/loginPage")        // 사용자 정의 로그인 페이지 URL
//                .loginProcessingUrl("/auth/login") // 로그인 처리 URL
//                    .defaultSuccessUrl("/")     // 로그인 성공 시 이동 url
//                    .usernameParameter("userId") // 로그인 시 사용할 파라미터 이름
//                    .passwordParameter("userPassword") // 사용자 정의 파라미터 이름 설정
//                    .failureHandler(customAuthenticationFailureHandler()) // 로그인 실패 시 로그인 실패 url 커스텀
//                    .permitAll()
//            )
////            .csrf(csrf -> csrf.disable())
//            .logout(logout -> logout
//                .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout")) // get으로 /logout 날려도 시큐리티에서 post로 인식
//                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동 url
//            )
//            .exceptionHandling(exception -> exception
//                .authenticationEntryPoint(new GrpAuthenticationEntryPoint()) // 인증되지 않은 사용자의 리소스 접근 시 수행되는 핸들러
//            );
//
//        return http.build();
//    }
//
//    // WebSecurity 설정 (필요 시 주석 해제)
//    // @Bean
//    // public WebSecurityCustomizer webSecurityCustomizer() {
//    //     return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/images/**");
//    // }
//
//    /**
//     * 암호화
//     * @return
//     */
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    /**
//     * 로그인 오류 시 각 url 지정
//     * @return
//     */
//    @Bean
//    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
//        return new CustomAuthenticationFailureHandler();
//    }
//
//}
