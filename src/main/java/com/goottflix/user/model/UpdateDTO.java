package com.goottflix.user.model;

import lombok.Data;

import java.util.Date;

@Data
public class UpdateDTO {
    private Long id;
    private String username;
    private String email;
    private Date birth;
    private User.Gender gender;
    private String profileImage;
}
