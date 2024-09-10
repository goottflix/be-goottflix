package com.goottflix.movie.model;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String email;
    private String password;
    private String username;
}
