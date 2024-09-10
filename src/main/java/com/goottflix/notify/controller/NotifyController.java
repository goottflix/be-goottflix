package com.goottflix.notify.controller;


import com.goottflix.notify.entity.FriendNotifyDTO;
import com.goottflix.notify.entity.NotifyEntity;
import com.goottflix.notify.service.NotifyService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.chrono.MinguoDate;
import java.util.List;


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

    // sse 구현
    @GetMapping(value = "/subscribe/{userId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@PathVariable Long userId) {
        return notifyService.subscribe(userId);
    }

    // 알림 읽음 확인
    @PutMapping("/read/{notifyId}")
    public ResponseEntity<?> readNotify(@RequestParam Long userId, @PathVariable Long notifyId) {
        try {
            // 서비스 계층에서 알림 읽음 처리
            notifyService.notifyRead(userId, notifyId);
            return ResponseEntity.ok("알림을 읽음");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("알림 읽음 처리 실패");
        }
    }
    // 알림 전체 조회
    @GetMapping("/all/{userId}")
    public ResponseEntity<List<NotifyEntity>> getAllNotify(@PathVariable Long userId) {
        try {
            List<NotifyEntity> notifyEntities = notifyService.getAllNotify(userId);
            return ResponseEntity.ok(notifyEntities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 친구 검색
    @GetMapping("/searchfriend")
    public ResponseEntity<List<FriendNotifyDTO>> searchFriend(@RequestParam String searchTerm) {
        List<FriendNotifyDTO> friend = notifyService.searchFriend(searchTerm);
        return ResponseEntity.ok(friend);
    }


}