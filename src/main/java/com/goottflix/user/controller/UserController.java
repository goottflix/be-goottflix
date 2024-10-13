package com.goottflix.user.controller;

import com.goottflix.user.jwt.JWTUtil;
import com.goottflix.user.model.UpdateDTO;
import com.goottflix.user.model.User;
import com.goottflix.user.model.UserListDTO;
import com.goottflix.user.model.repository.UserMapper;
import com.goottflix.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final JWTUtil jWTUtil;

    @GetMapping("/profile")
    public UserListDTO getUsers(@CookieValue("Authorization") String token) {
        UserListDTO myProfile = userMapper.findByUserId(jWTUtil.getUserID(token));
        System.out.println("myProfile = " + myProfile);
        return myProfile;
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
}
