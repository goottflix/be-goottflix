package com.goottflix.subscribe.service;

import com.goottflix.review.model.Review;
import com.goottflix.subscribe.mapper.SubscribeMapper;
import com.goottflix.subscribe.model.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeMapper subscribeMapper;

    public void save(Subscribe subscribe){
        if(subscribe.getId()==null){
            subscribeMapper.save(subscribe);
        }else{
            subscribeMapper.update(subscribe);
        }
    }

}
