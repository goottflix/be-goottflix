package com.goottflix.movie.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ReviewMovie {
    private Long movieId;
    private String title;
    private LocalDate releaseDate;
    private float rating;
    private String genre;
    private String director;
    private String nation;
    private String posterUrl;
    private int myRating;
    private LocalDate reviewDate;
    private String username;
}