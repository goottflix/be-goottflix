package com.goottflix.config;

import com.goottflix.user.jwt.JWTFilter;
import com.goottflix.user.jwt.JWTUtil;
import com.goottflix.user.jwt.LoginFilter;
import com.goottflix.user.service.CustomOAuth2UserService;
import com.goottflix.user.social.CustomSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomSuccessHandler customSuccessHandler;
    private final JWTUtil jwtUtil;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // security에서  cors 설정하기
        http
                .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(Collections.singletonList("http://goottflix.online")); // HTTPS로 변경
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(List.of("Set-Cookie", "Authorization"));
                        return configuration;
                    }
                })));
        //csrf disable
        http
                .csrf((auth) -> auth.disable());
        //form 로그인 disable
        http
                .formLogin((auth) -> auth.disable());
        //http basic 인증방식 disable
        http
                .httpBasic((auth) -> auth.disable());
        //oauth2
        http
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))
                        .successHandler(customSuccessHandler)
                );
        //경로별 인가작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/**").permitAll() //일단 뭐가 되는지부터 확인해보자..ㅠ
                        .requestMatchers("/book/**").permitAll() // 아두이노 요청
                        .requestMatchers("/auth/**").permitAll() // 회원가입 시 메일인증요청
                        .requestMatchers("/api/login","/api/join","/api/list/page","/files/**").permitAll() // 로그인페이지,메인페이지(영화목록), 파일업로드
                        .requestMatchers("/api/movie/write", "/api/movie/modify", "/api/movie/delete/**").hasRole("ADMIN")  // 'ROLE_ADMIN' 권한을 가진 사용자만 접근 가능
                        .anyRequest().authenticated());
        http
//                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
//        http
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }
}
