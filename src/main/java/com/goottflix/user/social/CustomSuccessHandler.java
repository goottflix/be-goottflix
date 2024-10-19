package com.goottflix.user.social;

import com.goottflix.user.jwt.JWTUtil;
import com.goottflix.user.model.User;
import com.goottflix.user.model.repository.UserMapper;
import com.goottflix.user.social.dto.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final UserMapper userMapper;

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse
            response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        String oauthId = customUserDetails.getName();
        User user = userMapper.findByOauthId(oauthId);

        String username = customUserDetails.getUsername();
        Long userId = customUserDetails.getUserId();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(userId, username, role, 60*60*60L*100);
        // 60*60*60L*100 = 6시간
        // 60*60*10L*100 = 1시간
        // 60*60*60L*10 = 36분
        response.addCookie(createCookie("Authorization", token));

        if (user.getUsername().equals("new") || user.getUsername().isEmpty()) {
            response.sendRedirect("https://goottflix.online/set-username");
        } else {
            response.sendRedirect("https://goottflix.online");
        }
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
//        cookie.setSecure(false); //개발환경에서 주석처리
        cookie.setMaxAge(60*60*60);
        cookie.setPath("/");

        return cookie;
    }
}
