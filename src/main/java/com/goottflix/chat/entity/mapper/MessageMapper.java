package com.goottflix.chat.entity.mapper;

import com.goottflix.chat.entity.MessageDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface MessageMapper {

    void insertMessage(Long chatRoomId, Long senderId, String content);


    List<MessageDTO> getMessagesByChatRoomId(Long chatRoomId);

}
