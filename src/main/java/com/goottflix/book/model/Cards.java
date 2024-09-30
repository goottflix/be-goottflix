package com.goottflix.book.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Cards {

    private Long id;
    private Long user_id;
    private String cardId; // 카드 UID를 byte 배열로 저장
    private LocalDateTime showTime;
    private int roomNumber;
    private String seatNumber;
    private Long movieId;
    private String entered;
}