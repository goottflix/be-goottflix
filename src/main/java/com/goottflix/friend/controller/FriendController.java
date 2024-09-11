package com.goottflix.friend.controller;


import com.goottflix.friend.entity.FriendEntity;
import com.goottflix.friend.entity.repository.FriendMapper;
import com.goottflix.friend.service.FriendService;
import com.goottflix.friend.entity.FriendNotifyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;


    // 친구 검색
    @GetMapping("/search")
    public ResponseEntity<List<FriendNotifyDTO>> searchFriend(@RequestParam String searchTerm) {
        List<FriendNotifyDTO> friend = friendService.searchFriend(searchTerm);
        return ResponseEntity.ok(friend);
    }

    // 친구 추가
    @PostMapping("/add")
    public ResponseEntity<String> addFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        friendService.addFriend(userId, friendId);
        return ResponseEntity.ok("친구 추가 성공");
    }

    // 친구 조회
    @GetMapping("list")
    public ResponseEntity<List<FriendNotifyDTO>> friendList(@RequestParam Long userId) {
        List<FriendNotifyDTO> friendList = friendService.friendList(userId);
        return ResponseEntity.ok(friendList);
    }
}
