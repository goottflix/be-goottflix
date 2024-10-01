package com.goottflix.chat.service;


import com.goottflix.chat.entity.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatRoom {

    private final ChatRoomMapper chatRoomMapper;



}
