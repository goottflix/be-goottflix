package com.goottflix.friend.entity;


import lombok.Data;

import java.sql.Timestamp;

@Data
public class FriendEntity {

    private Long id;
    private Long userId;
    private Long friendId;
    private Timestamp createdAt;

}
