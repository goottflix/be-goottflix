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

import java.util.HashMap;
import java.util.List;
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
    public void updateProfile(@RequestBody UpdateDTO user) {
        userService.updateProfile(user);
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
        System.out.println(myName);
        System.out.println("jWTUtil 에서 가져온 나의 이름은..= " + jWTUtil.getUsername(token));

        if (myName) {
            // 자신의 기존 닉네임
            response.put("available", true);  // 여전히 사용 가능
            return ResponseEntity.ok(response);
        } else {
            // 이미 존재하는 닉네임
            System.out.println("이미 존재하는 닉네임이야");
            response.put("available", false);  // 사용 불가능
            return ResponseEntity.ok(response);
        }
    }

}
