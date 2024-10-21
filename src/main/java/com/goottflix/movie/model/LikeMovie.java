package com.goottflix.movie.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LikeMovie {
    private Long movieId;
    private String title;
    private LocalDate releaseDate;
    private int rating;
    private String genre;
    private String director;
    private String posterUrl;
    private String nation;
    private int myRating;
    private int friendRating;

}
