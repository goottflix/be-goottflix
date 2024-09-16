package com.goottflix.user.controller;

import com.goottflix.user.jwt.JWTUtil;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JWTUtil jWTUtil;


    @GetMapping("/test")
    public String mainP(HttpServletRequest request) {
        // 쿠키에서 Authorization 토큰을 가져옴
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        // 토큰이 없을 경우 처리
        if (token == null) {
            return "JWT 토큰이 없습니다.";
        }

        // JWT에서 사용자 이름 추출
        String username = jWTUtil.getUsername(token);
        String role = jWTUtil.getRole(token);
        Long userId = jWTUtil.getUserID(token);
        Dotenv dotenv = Dotenv.load();
        System.out.println("GOOGLE_CLIENT_ID: " + dotenv.get("GOOGLE_CLIENT_ID"));
        System.out.println("NAVER_CLIENT_ID: " + dotenv.get("NAVER_CLIENT_ID"));


        return "test Controller :!! " + username+role+userId;
    }
    @GetMapping("/admin")
    public String adminP() {

        return "admin Controller";
    }
}
