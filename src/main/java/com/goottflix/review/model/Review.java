package com.goottflix.review.model;

import lombok.Data;

@Data
public class Review {
    private Long id;
    private Long movieId;
    private Long userId;
    private Long rating;
    private String review;
    private Long recommend;
    private Long spoiler;
}
