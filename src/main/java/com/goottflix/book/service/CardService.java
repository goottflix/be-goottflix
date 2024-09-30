package com.goottflix.book.service;

import com.goottflix.book.model.Cards;
import com.goottflix.book.model.repository.CardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {


    private final CardMapper cardMapper;

    @Transactional
    public boolean isCardRegistered(String cardId) {
        return cardMapper.countCardByUid(cardId) > 0;
    }

    @Transactional
    public boolean checkBook(String cardId, Long userId) {
        return cardMapper.countBookByUid(cardId,userId) > 0;
    }

    @Transactional
    public void saveCard(String cardId) {
        cardMapper.saveCardId(cardId);
    }

    @Transactional
    public void registerCard(String cardId, Long userId) {
        System.out.println("cardId = " + cardId);
        System.out.println("userId = " + userId);
        try{
            cardMapper.registerCard(cardId,userId);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void enterPerson(String cardId) {
        cardMapper.enteredMovie(cardId);
    }

}
