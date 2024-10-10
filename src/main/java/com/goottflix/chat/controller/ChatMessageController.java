package com.goottflix.chat.controller;

import com.goottflix.chat.entity.ChatMessage;
import com.goottflix.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessage message) {
        chatMessageService.insertChatMessage(message);
        messagingTemplate.convertAndSend("/topic/chat/" + message.getRoomId(), message);
    }

    @RestController
    @RequestMapping("/api/message")
    public static class MessageRestController {

        private final ChatMessageService chatMessageService;

        public MessageRestController(ChatMessageService chatMessageService) {
            this.chatMessageService = chatMessageService;
        }

        @GetMapping("/{roomId}")
        public List<ChatMessage> getMessagesByRoomId(@PathVariable Long roomId) {
            return chatMessageService.getMessagesByRoomId(roomId);
        }
    }
}