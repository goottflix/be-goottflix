package com.goottflix.subscribe.mapper;

import com.goottflix.subscribe.model.Subscribe;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubscribeMapper {

    void save(Subscribe subscribe);

    void update(Subscribe subscribe);
}
