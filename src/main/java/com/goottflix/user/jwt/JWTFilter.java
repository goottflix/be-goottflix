package com.goottflix.user.jwt;

import com.goottflix.user.model.CustomUserDetails;
import com.goottflix.user.model.User;
import com.goottflix.user.model.UserDTO;
import com.goottflix.user.social.dto.CustomOAuth2User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if(requestURI.equals("/api/join") || requestURI.equals("/api/login") ||
                requestURI.equals("/auth/sendCode") || requestURI.equals("/auth/verifyCode")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("Authorization")) {
                    authorization = cookie.getValue();
                    System.out.println("현재 유저의 JWT토큰 = " + authorization);
                    break;
                }
            }
        }


        if (authorization == null) {
            System.out.println("토큰이 없는데욤??");
            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization;
        //토큰 소멸 시간 검증
        if(jwtUtil.isExpired(token)) {
            System.out.println("토큰 유효기간이 만료됐슴당 ^ㅆ^");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);
        Long userId = jwtUtil.getUserID(token);

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setRole(role);
        userDTO.setUserId(userId);

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userDTO);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);

    }
}
