package com.goottflix.notify.entity.repository;


import com.goottflix.notify.entity.NotifyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface NotifyMapper {

    void insertNotify(NotifyEntity notifyEntity);

    void updateIsRead(@Param("userId") Long userId, @Param("notifyId") Long notifyId);
}
