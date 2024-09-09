package com.goottflix.controller;

import com.goottflix.movie.Service.MovieService;
import com.goottflix.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movie/write")
    public String write() {
        return "movieWrite";
    }

    @PostMapping("/movie/write")
    public String writePost(Movie movie, @RequestParam("file") MultipartFile file) throws IOException {
        movieService.save(movie, file);
        return "redirect:/movie/list";
    }

    @GetMapping("/movie/list")
    public String List(Model model) {
        List<Movie> list = movieService.getAllMovies();

        model.addAttribute("list", list);

        return "movieList";
    }

    @GetMapping("/movie/view")
    public String views(Model model, @RequestParam("id") Long id) {
        Movie movie = movieService.getMovieById(id);

        model.addAttribute("movie", movie);

        return "movieView";
    }
}
