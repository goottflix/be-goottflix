package com.goottflix.subscribe.model;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Subscribe {
    private Long id;
    private Long userId;
    private Timestamp subscribeStart;
    private Timestamp subscribeEnd;
}
