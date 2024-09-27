package com.goottflix.friend.service;

import com.goottflix.friend.entity.repository.FriendMapper;
import com.goottflix.friend.entity.FriendNotifyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
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

    // 친구 삭제
    public void removeFriend(Long userId, Long id) {
        Long friendId = friendMapper.findById(id);
    friendMapper.deleteFriend(userId, friendId);
    friendMapper.deleteFriend(friendId, userId);
    }

    // 친구 목록 조회
    public List<FriendNotifyDTO> friendList(Long userId) {
        return friendMapper.friendList(userId);
    }



}
