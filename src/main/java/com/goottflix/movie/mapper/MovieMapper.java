package com.goottflix.movie.mapper;

import com.goottflix.movie.model.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieMapper {

    Movie findById(Long id);

    List<Movie> findAll();

    List<Movie> findByRecommendedGenre(String genre);

    void save(Movie movie);

    void update(Movie movie);

    void delete(Long id);

    //영화 페이징
    List<Movie> getMoviesWithPage(@Param("size") int size, @Param("offset") int offset);
    int getTotalMovieCount();
}
