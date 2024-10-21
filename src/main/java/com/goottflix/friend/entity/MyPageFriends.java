package com.goottflix.friend.entity;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MyPageFriends {
    private Long friendId;
    private LocalDate createdAt;
    private String username;
    private String profileImage;
    private int watched;
    private int comment;
}
