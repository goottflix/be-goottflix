package com.goottflix.chat.entity.mapper;


import com.goottflix.chat.entity.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomMapper {

    List<ChatRoom> getAllChatRooms();
    ChatRoom getChatRoomById(Long id);
    int createChatRoom(ChatRoom chatRoom);
    int deleteChatRoom(Long id);

}
