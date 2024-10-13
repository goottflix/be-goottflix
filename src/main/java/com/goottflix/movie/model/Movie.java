package com.goottflix.movie.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Movie {

    private Long id;
    private String title;
    private String intro; // 영화 한줄설명 컬럼 추가
    private String description;
    private LocalDate releaseDate;
    private float rating;
    private String genre;
    private String director;
    private String posterUrl;
    private String videoUrl;
    private String nation; // 영화 제작국가 컬럼 추가
}
