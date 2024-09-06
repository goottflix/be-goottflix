package com.goottflix.dbapi.entity.repository;

import com.goottflix.dbapi.entity.ApiMovie;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiMovieRepository {


    void saveApiMovies(ApiMovie apiMovie);
}
