package com.goottflix.review.dto;

import com.goottflix.review.model.Review;
import lombok.Data;

@Data
public class ReviewAndNickname {
    private Review review;
    private String nickname;
    private Boolean isLikes;

    public ReviewAndNickname(Review review, String nickname, boolean isLikes) {
        this.review = review;
        this.nickname = nickname;
        this.isLikes = isLikes;
    }
}
