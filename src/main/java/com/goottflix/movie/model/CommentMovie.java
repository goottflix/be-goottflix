package com.goottflix.movie.model;


import lombok.Data;

import java.time.LocalDate;

//SELECT m.id, m.title, m.release_date, m.genre, r.review, r.recommend, r.spoiler, r.rating my_rating, r.review_date
//FROM review r
//JOIN movies m ON r.movie_id = m.id
//WHERE r.user_id = 1 and r.review IS NOT NULL;
@Data
public class CommentMovie {
    private Long movieId;
    private String title;
    private LocalDate releaseDate;
    private String genre;
    private String review;
    private String posterUrl;
    private int recommend;
    private int spoiler;
    private int myRating;
    private LocalDate reviewDate;
    private String username;
}
