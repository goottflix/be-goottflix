package com.goottflix.chat.entity.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ChatRoomMapper {

    // 채팅방 생성 메서드
    void createChatRoom(Map<String, Long> params);

    // 채팅방 ID 조회 메서드
    Long getChatRoomId(Map<String, Long> params);
}
