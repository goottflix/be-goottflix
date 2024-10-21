package com.goottflix.user.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class UserListDTO {
    private Long id;
    private String loginId;
    private String username;
    private String email;
    private Date birth;
    private User.Gender gender;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private String role;
    private boolean isActive;
    private String subscribe;
    private String profileImage;
    private int watched;
    private int comment;
    private int friends;
    private LocalDateTime expiration;
    private String likedGenre;
}