package com.goottflix.notify.controller;


import com.goottflix.notify.entity.NotifyEntity;
import com.goottflix.notify.service.NotifyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NotifyController {

    private final NotifyService notifyService;

    @PostMapping("/moiveupdate")
    public ResponseEntity<?> addMovieUpdate(@RequestBody NotifyEntity notifyEntity) {

        try {
            notifyService.addMovieUpdate(notifyEntity.getUserId(), notifyEntity.getMovieId());
            return ResponseEntity.ok("업데이트 알림 완료");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트에 실패했습니다");
        }
    }


}
