package com.goottflix.chat.entity.mapper;


import com.goottflix.chat.entity.ChatRoomDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomMapper {


    void insertChatRoom(Long userId1, Long userId2);

    ChatRoomDTO getChatRoomByUserIds(Long userId1, Long userId2);

    List<ChatRoomDTO> getAllChatRooms();

}
