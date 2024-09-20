package com.goottflix.movie.controller;

import com.goottflix.movie.model.Movie;
import com.goottflix.movie.service.MovieService;
import com.goottflix.review.model.Review;
import com.goottflix.review.service.ReviewService;
import com.goottflix.user.jwt.JWTUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class MovieApiController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final JWTUtil jWTUtil;

    @GetMapping("/list")
    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }

    @PostMapping("/review")
    public void addReview(@CookieValue("Authorization") String token,
                          @RequestParam("movieId") Long movieId,
                          @RequestParam("rating") int rating,
                          @RequestParam(name="review", required=false) String review,
                          HttpServletResponse res) throws IOException{
        Review review1 = new Review();

        review1.setUserId(jWTUtil.getUserID(token));
        review1.setMovieId(movieId);
        review1.setRating(rating);
        if(review!=null){
            review1.setReview(review);
        }

        reviewService.save(review1);

        float avg = reviewService.getAverageRatingByMovieId(movieId);
        movieService.updateRating(avg,movieId);
    }
}
