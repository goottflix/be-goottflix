package com.goottflix.review.mapper;

import com.goottflix.review.model.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    List<Review> findByMovieId(Long movieId);

    Review findById(Long id);

    void save(Review review);

    void update(Review review);
}
