package com.goottflix.chat.config;


import com.goottflix.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {


    private final MessageService messageService;

    // 현재 연결된 모든 WebSocket 세션을 관리하기 위한 Map
    private Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트가 보낸 메시지 payload 파싱
        String payload = message.getPayload();

        // 예시: "1:2:Hello, this is a message!"
        String[] parts = payload.split(":", 3);
        Long chatRoomId = Long.valueOf(parts[0]); // 첫 번째 값은 chatRoomId (Long 타입으로 변환)
        Long senderId = Long.valueOf(parts[1]); // 두 번째 값은 senderId (Long 타입으로 변환)
        String content = parts[2]; // 세 번째 값은 content

        // 메시지를 DB에 저장
        messageService.saveMessage(chatRoomId, senderId, content);

        // 모든 연결된 세션에 전송 (향후 특정 세션에만 전송하도록 개선 가능)
        for (WebSocketSession s : sessions.values()) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(payload));
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 새로운 클라이언트 연결 시 세션을 추가
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        // 클라이언트 연결 해제 시 세션을 제거
        sessions.remove(session.getId());
    }
}

