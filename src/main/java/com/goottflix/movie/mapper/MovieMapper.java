package com.goottflix.movie.mapper;

import com.goottflix.movie.model.CommentMovie;
import com.goottflix.movie.model.LikeMovie;
import com.goottflix.movie.model.Movie;
import com.goottflix.movie.model.ReviewMovie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    //영화 필터링
    List<Movie> getFilteredMovies(@Param("genre") String genre, @Param("nation") String nation,
                                  @Param("director") String director, @Param("sortBy") String sortBy);

    List<CommentMovie> getCommentMoviesByUserId(Long userId);
    List<ReviewMovie> getReviewMoviesByUserId(Long userId);
    List<LikeMovie> getLikeMoviesWithFriends(Map<String, Long> params);

}
