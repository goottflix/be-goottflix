package com.goottflix.notify.service;


import com.goottflix.notify.entity.NotifyEntity;
import com.goottflix.notify.entity.repository.NotifyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotifyService {

    private final NotifyMapper notifyMapper;


    public void addMovieUpdate(Long userId, Long movieId) {
        System.out.println("userId2222222 = " + userId);
        NotifyEntity notify = new NotifyEntity();
        notify.setContent("새로운 영화가 업데이트 되었습니다");
        notify.setUrl("/movie" + movieId);
        notify.setIsRead(false);
        notify.setNotifyType(NotifyEntity.NotifyType.movieUpdate);
        notify.setUserId(userId);
        notify.setMovieId(movieId);


        // 새로운 알림을 DB에 저장한다잉
        notifyMapper.insertNotify(notify);
    }

}
