package com.goottflix.movie.service;

import com.goottflix.movie.mapper.MovieMapper;
import com.goottflix.movie.model.Movie;
import com.goottflix.review.mapper.ReviewMapper;
import com.goottflix.review.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieMapper movieMapper;
    private final ReviewMapper reviewMapper;

    public void save(Movie movie, MultipartFile file) throws IOException {

        System.out.println(movie.getTitle());
        System.out.println(file.getOriginalFilename());
        if(!file.isEmpty()){
            movie.setPosterUrl(handleFileUpload(file));
        }
        if(movie.getId()==null){
            movieMapper.save(movie);
        }else{
            deleteExistingFile(movie);
            movieMapper.update(movie);
        }
    }

    public void updateRating(float avg, Long movieId){
        Movie movie1 = movieMapper.findById(movieId);
        movie1.setRating(avg);
        movieMapper.update(movie1);
    }

    private String handleFileUpload(MultipartFile file) throws IOException {
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);
        return "/files/"+fileName;
    }

    public void deleteExistingFile(Movie movie) {
        File oriFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static" + movie.getPosterUrl());
        if (oriFile.exists()) {
            oriFile.delete();
        }
    }

    public List<Movie> getAllMovies() {
        return movieMapper.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieMapper.findById(id);
    }

    public List<Movie> getRecommendedMovies(Long userId){
        List<Review> reviews = reviewMapper.findByRecommendedUserId(userId);

//        0 = 코미디
//        1 = 액션
//        2 = SF
//        3 = 어린이
//        4 = 공포
//        5 = 모험
        int[] genreCount = new int[6];   //인트 배열 크기는 장르의 갯수 만큼
        String[] genres = {"코미디", "액션", "SF", "어린이", "공포", "모험"};

        for(int i=0;i<reviews.size();i++){
            if(reviews.get(i).getRating()>=4){
                Movie movie = movieMapper.findById(reviews.get(i).getMovieId());
                if(movie.getGenre().contains(genres[0])){
                    genreCount[0]++;
                }else if(movie.getGenre().contains(genres[1])){
                    genreCount[1]++;
                }else if(movie.getGenre().contains(genres[2])){
                    genreCount[2]++;
                }else if(movie.getGenre().contains(genres[3])){
                    genreCount[3]++;
                }else if(movie.getGenre().contains(genres[4])){
                    genreCount[4]++;
                }else if(movie.getGenre().contains(genres[5])){
                    genreCount[5]++;
                }
            }
        }

        int max = 0;
        String maxGenre = "";

        for(int i=0;i<genres.length;i++){
            if(max<genreCount[i]){
                maxGenre = genres[i];
            }
        }

        return movieMapper.findByRecommendedGenre("%"+maxGenre+"%");
    }

    public void delete(Long id){
        movieMapper.delete(id);
    }
}
