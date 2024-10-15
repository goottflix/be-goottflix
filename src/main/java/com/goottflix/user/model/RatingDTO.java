package com.goottflix.user.model;

import lombok.Data;

@Data
public class RatingDTO {
    private int rating;
    private int userRatingCount;
    private int allRatingCount;
}
