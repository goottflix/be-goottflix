package com.goottflix.user.model.repository;

import com.goottflix.user.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    boolean existsByLoginId(String loginId);
    void joinIn(User user);

    User findByUserName(String username);

    User findByLoginId(String loginId);

    User findByOauthId(String oauthId);

    void updateUser(User user);
}
