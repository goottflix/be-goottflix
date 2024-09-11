package com.goottflix.friend.service;

import com.goottflix.friend.entity.repository.FriendMapper;
import com.goottflix.friend.entity.FriendNotifyDTO;
import kotlin.jvm.internal.FunInterfaceConstructorReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendMapper friendMapper;

    // 친구 검색
    public List<FriendNotifyDTO> searchFriend(String searchTerm) {
        return friendMapper.searchFriend(searchTerm);
    }

    // 친구 추가
    public void addFriend(Long userId, Long friendId) {
        friendMapper.addFriend(userId, friendId);
        friendMapper.addFriend(friendId, userId);

    }


}
