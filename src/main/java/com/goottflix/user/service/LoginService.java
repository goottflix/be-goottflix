package com.goottflix.user.service;

import com.goottflix.user.jwt.JWTUtil;
import com.goottflix.user.model.LoginDTO;
import com.goottflix.user.model.User;
import com.goottflix.user.model.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jWTUtil;

    public String login(LoginDTO loginDTO) {

        User user = userMapper.findByLoginId(loginDTO.getLoginId());
        System.out.println("user = " + user);

        if (user != null && passwordEncoder.matches(loginDTO.getPassword(), user.getPasswordHash())) {
            // 로그인 성공 시 JWT 토큰 생성
            String token = jWTUtil.createJwt(user.getId(), user.getUsername(), user.getRole(), 60 * 60 * 60L * 1000);
            return token;  // 토큰 반환
        } else {
            throw new IllegalArgumentException("로그인 실패");
        }
    }
}
