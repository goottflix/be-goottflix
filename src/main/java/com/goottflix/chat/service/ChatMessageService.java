package com.goottflix.chat.service;

import com.goottflix.chat.entity.ChatMessage;
import com.goottflix.chat.entity.mapper.ChatMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageMapper chatMessageMapper;


    public List<ChatMessage> getMessagesByRoomId(Long roomId) {
        return chatMessageMapper.getMessagesByRoomId(roomId);
    }

    public Long insertChatMessage(ChatMessage chatMessage) {
        return chatMessageMapper.insertChatMessage(chatMessage);
    }

    public void insertChatMessages(ChatMessage messages) {
        chatMessageMapper.insertChatMessages(messages);
    }

}
