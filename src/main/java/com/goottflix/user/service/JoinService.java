package com.goottflix.user.service;

import com.goottflix.user.model.JoinDTO;
import com.goottflix.user.model.User;
import com.goottflix.user.model.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();
        LocalDate birth = joinDTO.getBirth();
        User.Gender gender = joinDTO.getGender();
        String loginId = joinDTO.getLoginId();
        System.out.println("joinDTO = " + joinDTO);

        Boolean isExist = userMapper.existsByLoginId(loginId);
        System.out.println("isExist = " + isExist);

        if (isExist) {
            return ;
        }

        User data = new User();
        data.setLoginId(loginId);
        data.setUsername(username);
        data.setPasswordHash(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_USER");
        data.setEmail(email);
        data.setBirth(birth);
        data.setGender(gender);
        System.out.println("data = " + data);

        userMapper.joinIn(data);
    }
}
