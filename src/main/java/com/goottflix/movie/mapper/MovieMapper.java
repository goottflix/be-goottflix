package com.goottflix.movie.mapper;

import com.goottflix.movie.model.Movie;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MovieMapper {

    Movie findById(Long id);

    List<Movie> findAll();

    List<Movie> findByRecommendedGenre(String genre);

    void save(Movie movie);

    void update(Movie movie);

    void delete(Long id);
}
