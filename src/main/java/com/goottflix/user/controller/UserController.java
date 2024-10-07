package com.goottflix.user.controller;

import com.goottflix.user.jwt.JWTUtil;
import com.goottflix.user.model.UpdateDTO;
import com.goottflix.user.model.User;
import com.goottflix.user.model.UserDTO;
import com.goottflix.user.model.UserListDTO;
import com.goottflix.user.model.repository.UserMapper;
import com.goottflix.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/username/check")
    public ResponseEntity<String> checkUsername(@CookieValue("Authorization") String token,
                                                @RequestParam("username") String username) {
        boolean myName = jWTUtil.getUsername(token).equals(userMapper.findByUserName(username).getUsername());
        System.out.println("jWTUtil 에서 가져온 나의 이름은..= " + jWTUtil.getUsername(token));
        boolean exist = userMapper.existsByUsername(username);
        if (myName) {
            return ResponseEntity.ok("기존 별명입니다.");
        } else if (exist) {
            return ResponseEntity.badRequest().body("이미 존재하는 이름입니다.");
        } else {
            return ResponseEntity.ok().build();
        }
    }
}
