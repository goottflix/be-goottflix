package com.goottflix.friend.entity.repository;


import com.goottflix.friend.entity.FriendNotifyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendMapper {

    // 친구 검색
    List<FriendNotifyDTO> searchFriend();

    // 친구 추가
    void addFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

    List<FriendNotifyDTO> friendList(@Param("userId") Long userId);

    boolean existsByFriendId(Long userId, Long friendId);
}
