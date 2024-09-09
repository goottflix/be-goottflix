package com.goottflix.notify.controller;


import com.goottflix.notify.entity.NotifyEntity;
import com.goottflix.notify.service.NotifyService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notify")
public class NotifyController {

    private final NotifyService notifyService;


    // 영화 업데이트
    @PostMapping("/movieupdate")
    public ResponseEntity<?> addMovieUpdate(@RequestBody NotifyEntity notifyEntity) {
        try {
            notifyService.addMovieUpdate(notifyEntity.getUserId(), notifyEntity.getMovieId());
            return ResponseEntity.ok("업데이트 알림 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트에 실패했습니다");
        }
    }

    @GetMapping(value = "/")


    @PutMapping("/read/{notifyId}")
    public ResponseEntity<?> readNotify(@RequestParam Long userId, @PathVariable Long notifyId) {
        try {
            // 서비스 계층에서 알림 읽음 처리
            notifyService.notifyRead(userId, notifyId);
            return ResponseEntity.ok("알림을 읽음 처리했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알림 읽음 처리 실패");
        }

    }
}