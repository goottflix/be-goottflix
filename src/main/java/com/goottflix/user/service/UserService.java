package com.goottflix.user.service;

import com.goottflix.user.model.UpdateDTO;
import com.goottflix.user.model.User;
import com.goottflix.user.model.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public void updateProfile(UpdateDTO user) {

        if(user!=null){
            user.setId(user.getId());
            user.setUsername(user.getUsername());
            user.setEmail(user.getEmail());
            user.setBirth(user.getBirth());
            user.setGender(user.getGender());
            userMapper.updateProfile(user);
        } else {
            new IllegalArgumentException("user is null");
        }
    }
}
