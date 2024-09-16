package com.goottflix.user.model;

import lombok.Data;

@Data
public class UserDTO {

    private Long userId;
    private String oauthId;
    private String role;
    private String name;
    private String username;
}
