package com.goottflix.chat.entity;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessage {

    private Long id;
    private Long roomId;
    private String sender;
    private String message;
    private LocalDateTime timestamp;

}
