package com.goottflix.dbapi.entity;

import lombok.Data;

@Data
public class ApiMovie {


    private Long id;
    private String movieName; // 영화 이름
    private String director; // 감독 이름
    private String openDt; // 개봉일
    private String prdtYear; // 제작 연도
    private String nationAlt; // 국가 정보
    private String movieCd; // 영화 코드번호
    private String genre; // 영화 장르
    private String repGenre; // 대표장르인가?
    private String filename; // 이미지 넣을때
    private String filepath; // 이미지 넣을때


}
