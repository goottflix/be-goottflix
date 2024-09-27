package com.goottflix.user.controller;

import com.goottflix.user.jwt.JWTUtil;
import com.goottflix.user.model.UpdateDTO;
import com.goottflix.user.model.User;
import com.goottflix.user.model.UserDTO;
import com.goottflix.user.model.UserListDTO;
import com.goottflix.user.model.repository.UserMapper;
import com.goottflix.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void updateProfile(@RequestBody UpdateDTO user) {
        userService.updateProfile(user);
    }
}
