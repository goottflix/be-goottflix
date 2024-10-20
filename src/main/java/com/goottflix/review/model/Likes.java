package com.goottflix.review.model;

import lombok.Data;

@Data
public class Likes {
    private long id;
    private long userId;
    private long postId;
}
