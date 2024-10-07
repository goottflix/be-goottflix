package com.goottflix.book.model;

import com.goottflix.user.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BookInfo {
    private Long id;
    private String username;
    private String loginId;
    private Date birth;
    private User.Gender gender;
    private String genre;
    private String title;
    private String director;
    private String cardId;
    private int roomNumber;
    private String seatNumber;
    private LocalDateTime showTime;
    private String entered;
    private String result;
}
