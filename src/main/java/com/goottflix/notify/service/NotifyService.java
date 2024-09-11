package com.goottflix.notify.service;


import com.goottflix.friend.entity.FriendNotifyDTO;
import com.goottflix.notify.entity.NotifyEntity;
import com.goottflix.notify.entity.repository.NotifyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyService {

    private final NotifyMapper notifyMapper;
    private final Map<Long, SseEmitter> clients = new ConcurrentHashMap<>();

    // 영화 추가 알림 메소드
    public void addMovieUpdate(Long userId, Long movieId) {
        NotifyEntity notify = new NotifyEntity();
        notify.setContent("새로운 영화가 업데이트 되었습니다");
        notify.setUrl("/movie" + movieId); // 이 부분은 프론트에서 url 지정하는거에 따라 변화할거임
        notify.setIsRead(false);
        notify.setNotifyType(NotifyEntity.NotifyType.movieUpdate);
        notify.setUserId(userId);
        notify.setMovieId(movieId);


        // 새로운 알림을 DB에 저장한다잉
        notifyMapper.insertNotify(notify);


        sendNotify(userId, notify);

    }



    // sse로 알림 전송하는 메소드
    private void sendNotify(Long userId, NotifyEntity notify) {
        SseEmitter emitter = clients.get(userId);
        if(emitter != null) {
            try{
                emitter.send(SseEmitter.event().name("notify").data(notify));
            } catch (IOException e) {
                clients.remove(userId);
            }
        }
    }

    public SseEmitter subscribe(Long userId) {
        SseEmitter emitter = new SseEmitter();
        clients.put(userId, emitter);
        emitter.onCompletion(() -> clients.remove(userId)); // 연결 종료시 제거
        emitter.onTimeout(() -> clients.remove(userId)); // 시간 지나면 제거
        return emitter;
    }

    // 알림 확인 메서드
    public void notifyRead(Long userId, Long notifyId) {
        notifyMapper.updateIsRead(userId, notifyId);
    }

    // 알림 전체 조회 메소드
    public List<NotifyEntity> getAllNotify(Long userId) {
        return notifyMapper.findAllUserNotify(userId);
    }

}
