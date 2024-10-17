package com.goottflix.user.controller;

import com.goottflix.user.jwt.JWTUtil;
import com.goottflix.user.model.LoginDTO;
import com.goottflix.user.model.User;
import com.goottflix.user.model.repository.UserMapper;
import com.goottflix.user.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;
    private final JWTUtil jWTUtil;
    private final UserMapper userMapper;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        try {
            // 서비스에서 반환한 JWT 토큰
            String token = loginService.login(loginDTO);

            // JWT를 쿠키에 추가하여 응답
            response.addCookie(createCookie("Authorization", token));

            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        // 쿠키에서 JWT 토큰 가져오기
        Cookie[] cookies = request.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("Authorization".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 토큰이 없을 경우 처리
        if (token == null) {
            return ResponseEntity.status(401).body("No token found");
        }

        // JWT 토큰에서 사용자 정보 추출
        try {
            String username = jWTUtil.getUsername(token);
            String role = jWTUtil.getRole(token);
            System.out.println("로그인한 사용자 이름 = " + username);

            // JSON 형식으로 사용자 이름을 반환
            Map<String, String> response = new HashMap<>();
            response.put("username", username);
            response.put("role", role);

            return ResponseEntity.ok().body(response);  // JSON으로 응답
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token");
        }
    }



    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60 * 60 * 60); // 쿠키 만료 시간 설정
        cookie.setPath("/"); // 쿠키 경로 설정
        cookie.setHttpOnly(true); // 클라이언트 측에서 쿠키 접근 불가
//        cookie.setSecure(true); // HTTPS에서만 전송되도록 설정 (개발 환경에서만 주석 처리)
        return cookie;
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        response.addCookie(deleteCookie("Authorization", null));

        return ResponseEntity.ok("로그아웃 성공");

    }
    private Cookie deleteCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 쿠키 삭제를 위해 수명을 0으로 설정
        return cookie;
    }

    @PostMapping("/user/set-username")
    public ResponseEntity<String> setUsername(@RequestParam String username, @CookieValue("Authorization") String token) {

        Long userId = jWTUtil.getUserID(token);

        // DB에서 사용자 정보 가져오기
        User user = userMapper.findUserByUserId(userId);

        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        // username이 비어 있거나 유효하지 않으면 에러 반환
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Username cannot be empty");
        }

        // username 업데이트
        user.setUsername(username);
        userMapper.updateUser(user);

        return ResponseEntity.ok("Username updated successfully, redirecting to login");
    }

}
