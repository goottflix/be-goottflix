package com.goottflix.chat.controller;


import com.goottflix.chat.service.ChatRoomService;
import com.goottflix.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {


    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

    @PostMapping("/create-room")
    public ResponseEntity<?> createRoom(@RequestParam Long userId1, @RequestParam Long userId2) {
        return ResponseEntity.ok(chatRoomService.createChatRoom(userId1, userId2));
    }

    @GetMapping("/rooms")
    public ResponseEntity<?> getRooms() {
        return ResponseEntity.ok(chatRoomService.getAllChatRooms());
    }

    @GetMapping("/messages")
    public ResponseEntity<?> getMessages(@RequestParam Long chatRoomId) {
        return ResponseEntity.ok(messageService.getMessagesByChatRoomId(chatRoomId));
    }


}
