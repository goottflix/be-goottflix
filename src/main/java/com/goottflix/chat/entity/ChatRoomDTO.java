package com.goottflix.chat.entity;


import lombok.Data;

@Data
public class ChatRoomDTO {

    private Long id;
    private Long userId1;
    private Long userId2;
    private String createdAt;

}
