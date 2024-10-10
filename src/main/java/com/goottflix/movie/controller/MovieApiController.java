package com.goottflix.movie.controller;

import com.goottflix.movie.model.Movie;
import com.goottflix.movie.service.MovieService;
import com.goottflix.review.dto.ReviewAndNickname;
import com.goottflix.review.model.Review;
import com.goottflix.review.service.ReviewService;
import com.goottflix.subscribe.service.SubscribeService;
import com.goottflix.user.jwt.JWTUtil;
import com.goottflix.user.service.AdminService;
import com.goottflix.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "true")
@RequestMapping("/api")
public class MovieApiController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final SubscribeService subscribeService;
    private final JWTUtil jWTUtil;
    private final AdminService adminService;
    private final UserService userService;

    @GetMapping("/list")
    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/review")
    public List<ReviewAndNickname> getReview(@RequestParam("movieId") Long movieId) {
        List<Review> reviews = reviewService.getReviewByMovieId(movieId);
        List<ReviewAndNickname> reviewAndNicknames = new ArrayList<>();

        for(Review review : reviews) {
            String nickname = userService.findUsernameByuserId(review.getUserId());
            reviewAndNicknames.add(new ReviewAndNickname(review, nickname));
        }

        return reviewAndNicknames;
    }

    @PostMapping("/review")
    public void addReview(@CookieValue("Authorization") String token,
                          @RequestParam("movieId") Long movieId,
                          @RequestParam("rating") int rating,
                          @RequestParam(name="review", required=false) String review){
        Review review1 = new Review();

        if(jWTUtil.getUserID(token)!=null){
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

    @GetMapping("/recommendedList")
    public List<Movie> getRecommendedMovies(@CookieValue("Authorization") String token) {
        Long userId = jWTUtil.getUserID(token);
        if(userId!=null){
            return movieService.getRecommendedMovies(userId);
        }else{
            return null;
        }
    }

    @PostMapping("/recommendUp")
    public void recommendUp(@RequestParam("userId") Long userId){
        reviewService.recommendUp(userId);
    }

    @PostMapping("/subscribe")
    public void subscribe(@CookieValue("Authorization") String token){
        boolean saved = subscribeService.save(jWTUtil.getUserID(token));

        if (saved) {
            adminService.setUserSubscribe(jWTUtil.getUserID(token));
        }
    }

    @PostMapping("/movie/write")
    public ResponseEntity<?> writePost(Movie movie, @RequestParam("file") MultipartFile file) throws IOException {
        movieService.save(movie, file);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/userSubscribe")
    public boolean userSubscribe(@CookieValue("Authorization") String token){
        boolean isSubscribe = userService.getUserSubscribe(jWTUtil.getUserID(token)).equals("subscribe");
        return isSubscribe;
    }
}
