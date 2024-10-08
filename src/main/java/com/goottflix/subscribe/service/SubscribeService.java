package com.goottflix.subscribe.service;

import com.goottflix.review.model.Review;
import com.goottflix.subscribe.mapper.SubscribeMapper;
import com.goottflix.subscribe.model.Subscribe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeMapper subscribeMapper;

    public boolean save(Long userId){

        try{
            Subscribe subscribe = subscribeMapper.findByUserId(userId);

            if(subscribe==null){
                subscribe=new Subscribe();
                subscribe.setUserId(userId);
                subscribe.setSubscribeStart(LocalDate.now());
                subscribe.setSubscribeEnd(LocalDate.now().plusMonths(1));
                subscribeMapper.save(subscribe);
                return true;
            }else{
                if(LocalDate.now().isAfter(subscribe.getSubscribeEnd())){
                    subscribe.setSubscribeEnd(subscribe.getSubscribeEnd().plusMonths(1));
                }else if(LocalDate.now().isBefore(subscribe.getSubscribeEnd())){
                    subscribe.setSubscribeStart(LocalDate.now());
                    subscribe.setSubscribeEnd(LocalDate.now().plusMonths(1));
                }
                subscribeMapper.update(subscribe);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
