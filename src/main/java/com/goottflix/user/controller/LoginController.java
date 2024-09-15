package com.goottflix.user.controller;

import com.goottflix.user.model.LoginDTO;
import com.goottflix.user.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        try {
            // 서비스에서 반환한 JWT 토큰
            String token = loginService.login(loginDTO);

            // JWT를 쿠키에 추가하여 응답
            response.addCookie(createCookie("Authorization", token));
            System.out.println("token = " + token);
            System.out.println("response = " + response);

            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60); // 쿠키 만료 시간 설정
        cookie.setPath("/"); // 쿠키 경로 설정
        cookie.setHttpOnly(true); // 클라이언트 측에서 쿠키 접근 불가
        //cookie.setSecure(true); // HTTPS에서만 전송되도록 설정 (개발 환경에서만 주석 처리)
        return cookie;
    }
}
