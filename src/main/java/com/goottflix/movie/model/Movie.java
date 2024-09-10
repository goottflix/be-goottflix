package com.goottflix.movie.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Movie {

    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private float rating;
    private String genre;
    private String director;
    private String posterUrl;
}
