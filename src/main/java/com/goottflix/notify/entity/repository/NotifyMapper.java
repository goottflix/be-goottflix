package com.goottflix.notify.entity.repository;


import com.goottflix.friend.entity.FriendNotifyDTO;
import com.goottflix.notify.entity.NotifyEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotifyMapper {

    void insertNotify(NotifyEntity notifyEntity);

    void updateIsRead(Long userId,Long notifyId);

    List<NotifyEntity> findAllUserNotify(Long userId);

    void deleteNotify(Long notifyId);
}
