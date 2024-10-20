package com.goottflix.review.model;

import lombok.Data;

@Data
public class ReviewRank {
    private Long userId;
    private int reviewRank;
    private int reviewPercent;

}
