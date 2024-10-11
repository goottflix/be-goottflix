package com.goottflix.user.model.repository;

import com.goottflix.user.model.LoginDTO;
import com.goottflix.user.model.UpdateDTO;
import com.goottflix.user.model.User;
import com.goottflix.user.model.UserListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    void joinIn(User user);
    void setUpdateLastLogin(Long id);

    User findByUserName(String username);

    User findByLoginId(String loginId);

    User findByOauthId(String oauthId);

    void updateUser(User user);

    List<Long> findAllUserId();

    String getUserName(Long userID);

    //AdminService (관리자페이지)
    List<UserListDTO> getUserList();
    void setUserAdmin(Long userId);
    void setUserUser(Long userId);
//    void setUserRole(Long userId);
    void setUserSubscribe(Long userId);
    void setUserExpired(Long userId);

    String getUserSubscribe(Long userId);

    User findUserByUserId(Long userId);
    //UserService (마이페이지)
    UserListDTO findByUserId(Long userId);
    void updateProfile(UpdateDTO user);
    //아이디 비번찾기
    LoginDTO findByEmail(String email);
    void setUserNewPassword(LoginDTO loginDTO);
}
