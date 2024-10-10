package com.goottflix.board.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoradEntity {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
