package com.goottflix.notify.service;


import com.goottflix.notify.entity.NotifyEntity;
import com.goottflix.notify.entity.repository.NotifyMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyService {

    private final NotifyMapper notifyMapper;
    private final CopyOnWriteArrayList<HttpServletResponse> clients = new CopyOnWriteArrayList<>();

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

    }

    public void notifyRead(Long userId, Long notifyId) {
        notifyMapper.updateIsRead(userId, notifyId);
    }

}
