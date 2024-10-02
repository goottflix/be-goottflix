package com.goottflix.chat.service;


import com.goottflix.chat.entity.MessageDTO;
import com.goottflix.chat.entity.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageMapper messageMapper;

    public void saveMessage(Long chatRoomId, Long senderId, String content) {
        messageMapper.insertMessage(chatRoomId, senderId, content);
    }

    public List<MessageDTO> getMessagesByChatRoomId(Long chatRoomId) {
        return messageMapper.getMessagesByChatRoomId(chatRoomId);
    }

}
