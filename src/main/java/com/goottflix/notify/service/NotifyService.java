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
        notify.setUrl("/movie" + movieId);
        notify.setIsRead(false);
        notify.setNotifyType(NotifyEntity.NotifyType.movieUpdate);
        notify.setUserId(userId);
        notify.setMovieId(movieId);


        // 새로운 알림을 DB에 저장한다잉
        notifyMapper.insertNotify(notify);

        // 업데이트 된 알림을 모든 클라이언트에게 전달 하는 거인듯..?
        notifyClients("새로운 영화가 업데이트 되었습니다" + movieId);
    }

    // sse 관련한 메서드임
    public void streamNotify(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.TEXT_EVENT_STREAM_VALUE);
        response.setCharacterEncoding("UTF-8");
        clients.add(response);
    }

    //  저장된 모든 클라이언트한테 알림을 전송하는 메서드임
    public void notifyClients(String content) {
        clients.forEach(client -> {
            try {
                client.getWriter().write("data: " + content + "\n\n");
                client.getWriter().flush();
            }catch (IOException e) {
                clients.remove(client);
            }
        });
    }


}
