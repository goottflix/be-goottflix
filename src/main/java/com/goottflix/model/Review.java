package com.goottflix.model;

import lombok.Data;

@Data
public class Review {
    private Long id;
    private Long movieId;
    private String username;
    private int rating;
    private String review;
    private int recommend;
}
