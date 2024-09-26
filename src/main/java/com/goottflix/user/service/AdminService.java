package com.goottflix.user.service;

import com.goottflix.user.model.UserListDTO;
import com.goottflix.user.model.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserMapper userMapper;

    public void setAdmin (Long userId){
        if (userId!=null) {
            userMapper.setUserAdmin(userId);
            System.out.println("userId = " + userId);
            System.out.println("관리자로 변경");
        }
    }
    public void setUser (Long userId){
        userMapper.setUserUser(userId);
    }

    public void setUserSubscribe (Long userId){
        userMapper.setUserSubscribe(userId);
    }
    public void setUserExpired (Long userId){
        userMapper.setUserExpired(userId);
    }

//    public List<UserListDTO> getUserList() {
//        List<UserListDTO> userList = userMapper.getUserList();
//
//        // 각 UserListDTO의 createdAt과 lastLogin을 ZonedDateTime으로 변환
//        for (UserListDTO user : userList) {
//            if (user.getCreatedAt() != null) {
//                user.setCreatedAt(convertToZonedDateTime(user.getCreatedAt().toLocalDateTime()));
//            }
//            if (user.getLastLogin() != null) {
//                user.setLastLogin(convertToZonedDateTime(user.getLastLogin().toLocalDateTime()));
//            }
//        }
//
//        return userList;
//    }
//
//    private ZonedDateTime convertToZonedDateTime(LocalDateTime localDateTime) {
//        if (localDateTime == null) {
//            return null;
//        }
//        return localDateTime.atZone(ZoneId.systemDefault());
//    }
}
