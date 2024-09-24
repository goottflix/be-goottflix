package com.goottflix.friend.service;

import com.goottflix.friend.entity.repository.FriendMapper;
import com.goottflix.friend.entity.FriendNotifyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendMapper friendMapper;

    // 친구 검색
    public List<FriendNotifyDTO> searchFriend() {
        return friendMapper.searchFriend();
    }

    // 친구 추가
    public void addFriend(Long userId, Long friendId) {
        friendMapper.addFriend(userId, friendId);
        friendMapper.addFriend(friendId, userId);

    }

    // 친구 목록 조회
    public List<FriendNotifyDTO> friendList(Long userId) {
        return friendMapper.friendList(userId);
    }



}
