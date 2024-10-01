package com.goottflix.chat.entity;


import lombok.Data;

@Data
public class MessageDTO {

    private Long id;
    private Long chatRoomId;
    private Long senderId;
    private String content;
    private String sentAt;

}
