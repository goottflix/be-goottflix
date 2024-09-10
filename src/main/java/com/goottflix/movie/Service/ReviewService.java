package com.goottflix.movie.Service;

import com.goottflix.model.Movie;
import com.goottflix.model.Review;
import com.goottflix.movie.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    public List<Review> getReviewByMovieId(Long movieId){
        return reviewMapper.findByMovieId(movieId);
    }

    public void save(Review review){
        if(review.getId()==null){
            reviewMapper.save(review);
        }else{
            reviewMapper.update(review);
        }
    }

    public float getAverageRatingByMovieId(Long movieId){
        List<Review> reviews = getReviewByMovieId(movieId);
        float averageRating = 0;
        for(Review review : reviews){
            averageRating+=review.getRating();
        }
        return averageRating/reviews.size();
    }

    public void recommendUp(Long reviewId){
        Review review = reviewMapper.findById(reviewId);
        review.setRecommend(review.getRecommend()+1);
        reviewMapper.update(review);
    }
}
