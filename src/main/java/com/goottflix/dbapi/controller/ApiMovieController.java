package com.goottflix.dbapi.controller;

import com.goottflix.dbapi.service.MovieApiService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiMovieController {

    private final MovieApiService movieApiService;

    @PostMapping("getMovie")
    public ResponseEntity<?> fetchMovie() {

        movieApiService.fetchAndSaveMovies();
        return ResponseEntity.ok().body("잘됐으잘돼쓰");
    }


    @PostMapping("get/bestMovie")
    public ResponseEntity<?> fetchBestMovie() {

        movieApiService.fetchAndSaveWeeklyBoxOffice();
        return ResponseEntity.ok().body("잘됐으잘돼쓰");
    }

    @PostMapping("get/bestMovie2")
    public ResponseEntity<?> fetchBestMovie2(@RequestBody RequestDate requestDate) {

        movieApiService.fetchAndSaveWeeklyBoxOffice2(requestDate.targetDt);
        return ResponseEntity.ok().body("잘됐으잘돼쓰");
    }

    @Data
    static class RequestDate {
        private String targetDt;

        public RequestDate() {}

        public RequestDate(String targetDt) {
            this.targetDt = targetDt;
        }
    }
}
