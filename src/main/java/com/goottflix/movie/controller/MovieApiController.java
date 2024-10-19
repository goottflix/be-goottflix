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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "true")
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
    @GetMapping("/list/page")
    public ResponseEntity<Map<String, Object>> getMoviesPage(@RequestParam int page, @RequestParam int size) {
        List<Movie> movies = movieService.getMoviesWithPage(page, size);
        int totalMovies = movieService.getTotalMovieCount();

        Map<String, Object> response = new HashMap<>();
        response.put("movies", movies);
        response.put("totalMovies", totalMovies);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/review")
    public List<ReviewAndNickname> getReview(@RequestParam("movieId") Long movieId, @CookieValue("Authorization") String token) {
        List<Review> reviews = reviewService.getReviewByMovieId(movieId);
        List<ReviewAndNickname> reviewAndNicknames = new ArrayList<>();

        for(Review review : reviews) {
            String nickname = userService.findUsernameByuserId(review.getUserId());
            boolean likes = reviewService.isLiked(review.getId(), jWTUtil.getUserID(token));
            reviewAndNicknames.add(new ReviewAndNickname(review, nickname, likes));
        }

        return reviewAndNicknames;
    }

    @PostMapping("/review")
    public void addReview(@CookieValue("Authorization") String token,
                          @RequestParam("movieId") Long movieId,
                          @RequestParam("rating") Long rating,
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

    @PostMapping("reviewUpdate")        //리뷰 수정
    public void updateReview(@RequestParam("reviewId") Long reviewId, @RequestParam("review") String review, @RequestParam("rating") Long rating){
        Review review1 = new Review();
        review1.setId(reviewId);
        review1.setRating(rating);
        if(review!=null){
            review1.setReview(review);
        }
        reviewService.save(review1);
    }

    @PostMapping("/reviewDelete")       //리뷰 삭제
    public void deleteReview(@RequestParam("reviewId") Long reviewId){
        reviewService.delete(reviewId);
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
    public void recommendUp(@RequestParam("reviewId") Long reviewId, @CookieValue("Authorization") String token){
        reviewService.recommendUp(reviewId, jWTUtil.getUserID(token));
    }

    @PostMapping("/subscribe")
    public void subscribe(@CookieValue("Authorization") String token){
        boolean saved = subscribeService.save(jWTUtil.getUserID(token));

        if (saved) {
            adminService.setUserSubscribe(jWTUtil.getUserID(token));
        }
    }

    @GetMapping("/movie/{movieId}")
    public Movie getMovie(@PathVariable("movieId") Long movieId) {
        return movieService.getMovieById(movieId);
    }


    @PostMapping("/movie/write")
    public ResponseEntity<?> writePost(Movie movie, @RequestParam("file") MultipartFile file) throws IOException {
        movieService.save(movie, file);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/movie/modify")
    public ResponseEntity<?> modifyPost(Movie movie, @RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("movie.getId() = " + movie.getId());
        System.out.println("movie.getIntro() = " + movie.getIntro());
        movieService.modify(movie,file);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/movie/delete/{movieId}")
    public void deleteMovie(@PathVariable("movieId") Long movieId){
        System.out.println("movieId = " + movieId);
        try {
            movieService.delete(movieId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @GetMapping("/userSubscribe")
    public boolean userSubscribe(@CookieValue("Authorization") String token){
        boolean isSubscribe = userService.getUserSubscribe(jWTUtil.getUserID(token)).equals("subscribe");
        return isSubscribe;
    }

    @PostMapping("/declaration")
    public void declaration(@RequestParam("reviewId") Long reviewId){
        reviewService.declaration(reviewId);
    }

    @GetMapping("/spoilerReview")       //스포일러 리뷰들 가져오기
    public List<Review> getSpoilerReview(){
        return reviewService.getReviewBySpoiler();
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> getFilteredMovies(@RequestParam(required = false) String genre,
                                                        @RequestParam(required = false) String nation,
                                                        @RequestParam(required = false) String director,
                                                        @RequestParam(required = false) String sortBy) {
        List<Movie> movies = movieService.getFilteredMovies(genre, nation, director, sortBy);
        return ResponseEntity.ok(movies);
    }
}
