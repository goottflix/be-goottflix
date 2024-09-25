package com.goottflix.subscribe.model;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDate;

@Data
public class Subscribe {
    private Long id;
    private Long userId;
    private LocalDate subscribeStart;
    private LocalDate subscribeEnd;
}
