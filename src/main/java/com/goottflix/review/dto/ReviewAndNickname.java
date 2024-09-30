package com.goottflix.review.dto;

import com.goottflix.review.model.Review;
import lombok.Data;

@Data
public class ReviewAndNickname {
    private Review review;
    private String nickname;

    public ReviewAndNickname(Review review, String nickname) {
        this.review = review;
        this.nickname = nickname;
    }
}
