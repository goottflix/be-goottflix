package com.goottflix.notify.entity.repository;


import com.goottflix.notify.entity.NotifyEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NotifyMapper {

    void insertNotify(NotifyEntity notifyEntity);


}
