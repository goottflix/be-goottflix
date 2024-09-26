package com.goottflix.user.controller;

import com.goottflix.user.model.User;
import com.goottflix.user.model.UserListDTO;
import com.goottflix.user.model.repository.UserMapper;
import com.goottflix.user.service.AdminService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserMapper userMapper;
    private final AdminService adminService;

    @GetMapping("/userList")
    public List<UserListDTO> getUserList() {
        return userMapper.getUserList();
    }

    @PostMapping("/setAdmin")
    public void setAdmin(@RequestBody UserSetRole setRole) {
        adminService.setAdmin(setRole.getId());
    }
    @PostMapping("/setUser")
    public void setUser(@RequestBody UserSetRole setRole) {
        adminService.setUser(setRole.getId());
    }
    //유저 권한변경
    @Data
    static class UserSetRole {
        private Long id;
    }

}
