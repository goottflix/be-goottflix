package com.goottflix.notify.entity;


import lombok.Data;

@Data
public class NotifyEntity {

    private Long id;                // 알림의 ID
    private String content;         // 알림 내용
    private String url;             // 알림과 관련된 URL
    private Boolean isRead;// 알림이 읽혔는지 여부
    private NotifyType notifyType;  // 알림 타입
    private Long userId;
    private Long movieId;



    public enum NotifyType {
        movieUpdate, friendadd //알림 종류
    }


}

