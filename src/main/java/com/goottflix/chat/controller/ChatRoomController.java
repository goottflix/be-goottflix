package com.goottflix.chat.controller;

import com.goottflix.chat.entity.ChatRoom;
import com.goottflix.chat.service.ChatRoomService;
import com.goottflix.user.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/chatroom")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final JWTUtil jwtUtil;

    @GetMapping
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomService.getAllChatRooms();
    }

    @GetMapping("/{id}")
    public ChatRoom getChatRoomById(@PathVariable Long id) {
        return chatRoomService.getChatRoomById(id);
    }

    @PostMapping("/createroom")
    public ChatRoom createChatRoom(@RequestBody ChatRoom chatRoom) {
        return chatRoomService.createChatRoom(chatRoom);
    }

    @DeleteMapping("/{id}")
    public int deleteChatRoom(@PathVariable Long id) {
        return chatRoomService.deleteChatRoom(id);
    }

    @GetMapping("/getusername")
    public ResponseEntity<?> userName(@CookieValue("Authorization") String token) {
        String username = jwtUtil.getUsername(token);
        System.out.println("username = " + username);
        return ResponseEntity.ok(Collections.singletonMap("username", username));
    }
}
