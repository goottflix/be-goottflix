package com.goottflix.user.controller;

import com.goottflix.user.jwt.JWTUtil;
import com.goottflix.user.model.RatingDTO;
import com.goottflix.user.model.UpdateDTO;
import com.goottflix.user.model.User;
import com.goottflix.user.model.UserListDTO;
import com.goottflix.user.model.repository.UserMapper;
import com.goottflix.user.service.UserService;
import com.goottflix.user.service.VericiationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JWTUtil jWTUtil;
    private final VericiationService vericiationService;

    @GetMapping("/profile")
    public Map<String, Object> getUsers(@CookieValue("Authorization") String token) {
        Long userId = jWTUtil.getUserID(token);

        // 각 DTO 가져오기
        UserListDTO myProfile = userMapper.findByUserId(userId);
        System.out.println("myProfile = " + myProfile);
        List<RatingDTO> myRating = userMapper.getRatingByUserId(userId);
        System.out.println("myRating = " + myRating);

        // Map을 사용해 두 가지 데이터를 묶어서 반환
        Map<String, Object> response = new HashMap<>();
        response.put("userProfile", myProfile);
        response.put("userRating", myRating);

        // 데이터 확인용 로그
        System.out.println("myProfile = " + myProfile);
        System.out.println("myRating = " + myRating);

        return response;  // Map으로 두 가지 데이터를 반환
    }
    @PostMapping("/profile/update")
    public void updateProfile(@RequestPart("user") UpdateDTO user,
                              @RequestPart("file") MultipartFile file,
                              @CookieValue("Authorization") String token) {
        try {
            Long userId = jWTUtil.getUserID(token);
            user.setId(userId);

            userService.updateProfile(user, file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @PostMapping("/username/check")
    public ResponseEntity<Map<String, Boolean>> checkUsername(@CookieValue("Authorization") String token,
                                                              @RequestParam("username") String username) {
        Map<String, Boolean> response = new HashMap<>();

        User user = userMapper.findByUserName(username);
        if (user == null) {
            response.put("available", true);
            return ResponseEntity.ok(response);
        }
        boolean myName = jWTUtil.getUsername(token).equals(user.getUsername());
        if (myName) {
            response.put("available", true);
            return ResponseEntity.ok(response);
        } else {
            response.put("available", false);
            return ResponseEntity.ok(response);
        }
    }
    @GetMapping("/request/verifycode")
    public String verifyCode(@CookieValue("Authorization") String token) {
        Long userId = jWTUtil.getUserID(token);
        String username = jWTUtil.getUsername(token);
        if (userId !=null) {
            String verificationCode = String.format("%06d", new Random().nextInt(1000000));
            vericiationService.saveVerficationCodeWithDraw(username, verificationCode);
            return verificationCode;
        }
        return null;
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<?> withdraw(@CookieValue("Authorization") String token, HttpServletResponse response) {

        Long userId = jWTUtil.getUserID(token);
        if (userId != null) {
            userMapper.withdrawUser(userId);
            response.addCookie(deleteCookie("Authorization", null));
            return ResponseEntity.ok("회원 탈퇴 및 로그아웃 성공");
        } else {
            return ResponseEntity.badRequest().body("유효하지 않은 사용자입니다.");
        }
    }
    private Cookie deleteCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); // 쿠키 삭제를 위해 수명을 0으로 설정
        return cookie;
    }

}
