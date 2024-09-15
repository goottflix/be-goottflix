package com.goottflix.user.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class JoinDTO {

    private String loginId;
    private String username;
    private String password;
    private String email;
    private LocalDate birth;
    private User.Gender gender;
}
