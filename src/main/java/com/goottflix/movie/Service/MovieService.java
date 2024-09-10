package com.goottflix.movie.Service;

import com.goottflix.movie.mapper.MovieMapper;
import com.goottflix.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {


    private final MovieMapper movieMapper;

    public void save(Movie movie, MultipartFile file) throws IOException {

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

    public void delete(Long id){
        movieMapper.delete(id);
    }
}
