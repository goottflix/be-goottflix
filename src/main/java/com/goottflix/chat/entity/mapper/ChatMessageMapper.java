package com.goottflix.chat.entity.mapper;


import com.goottflix.chat.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMessageMapper {

    List<ChatMessage> getMessagesByRoomId(Long roomId);
    Long insertChatMessage(ChatMessage chatMessage);

}
