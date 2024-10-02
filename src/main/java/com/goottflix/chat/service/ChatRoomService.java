package com.goottflix.chat.service;


import com.goottflix.chat.entity.ChatRoomDTO;
import com.goottflix.chat.entity.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomMapper chatRoomMapper;


    public ChatRoomDTO createChatRoom(Long userId1, Long userId2) {
        chatRoomMapper.insertChatRoom(userId1, userId2);
        return chatRoomMapper.getChatRoomByUserIds(userId1, userId2);
    }

    public List<ChatRoomDTO> getAllChatRooms() {
        return chatRoomMapper.getAllChatRooms();
    }

}
