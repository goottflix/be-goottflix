package com.goottflix.friend.controller;



import com.goottflix.friend.entity.repository.FriendMapper;
import com.goottflix.friend.service.FriendService;
import com.goottflix.friend.entity.FriendNotifyDTO;
import com.goottflix.notify.service.NotifyService;
import com.goottflix.user.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class FriendController {

    private final FriendService friendService;
    private final JWTUtil jwtUtil;
    private final NotifyService notifyService;
    private final FriendMapper friendMapper;

    // 친구 검색
    @GetMapping("/search")
    public ResponseEntity<List<FriendNotifyDTO>> searchFriend() {
        List<FriendNotifyDTO> friend = friendService.searchFriend();
        return ResponseEntity.ok(friend);
    }

    // 친구 추가
    @PostMapping("/add")
    public ResponseEntity<?> addFriend(@CookieValue("Authorization") String token, @RequestParam Long friendId) {

        boolean exist = friendMapper.existsByFriendId(jwtUtil.getUserID(token), friendId);
        if(exist) {
            return ResponseEntity.badRequest().body("추가 실패");
        }
        friendService.addFriend(jwtUtil.getUserID(token), friendId);
        notifyService.addFriendUpdate(friendId);
        return ResponseEntity.ok("친구 추가 성공");
    }

    // 친구 삭제
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFriend(@CookieValue("Authorization") String token, @RequestParam Long id) {
        Long userId = jwtUtil.getUserID(token);
        System.out.println("userId = " + userId);
        System.out.println("id = " + id);
        friendService.removeFriend(userId, id);
        return ResponseEntity.ok("친구 삭제완료");
    }

    // 친구 목록
    @GetMapping("/list")
    public ResponseEntity<List<FriendNotifyDTO>> friendList(@CookieValue("Authorization") String token) {
        List<FriendNotifyDTO> friendList = friendService.friendList(jwtUtil.getUserID(token));
        return ResponseEntity.ok(friendList);
    }
}
