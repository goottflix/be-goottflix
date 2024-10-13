package com.goottflix.user.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User {

    private Long id;
    private String username;
    private String loginId;
    private String email;
    private String passwordHash;
    private String oauthId;
    private LocalDate birth;
    private Gender gender;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private String role;
    private boolean isActive;
    private String preference;
    private String subscribe;
    private String profileImage;

    public enum Gender {
        M, F
    }

}




