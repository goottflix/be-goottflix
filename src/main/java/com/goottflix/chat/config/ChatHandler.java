package com.goottflix.chat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goottflix.chat.entity.ChatMessage;
import com.goottflix.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ChatMessageService chatMessageService;
    private final Map<Long, List<WebSocketSession>> roomSessions = new HashMap<>();



    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 연결 시 채팅방 ID를 세션에 저장
        Long roomId = getRoomId(session);
        roomSessions.computeIfAbsent(roomId, k -> new ArrayList<>()).add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 메시지 수신 시 모든 클라이언트에 브로드캐스트
        Long roomId = getRoomId(session);
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        chatMessage.setRoomId(roomId);
        chatMessageService.insertChatMessage(chatMessage);

        String broadcastMessage = objectMapper.writeValueAsString(chatMessage);
        for (WebSocketSession sess : roomSessions.get(roomId)) {
            sess.sendMessage(new TextMessage(broadcastMessage));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 세션 종료 시 목록에서 제거
        Long roomId = getRoomId(session);
        roomSessions.get(roomId).remove(session);
    }

    private Long getRoomId(WebSocketSession session) {
        Map<String, Object> attributes = session.getAttributes();
        return (Long) attributes.get("roomId");
    }
}