package com.goottflix.book.model.repository;

import com.goottflix.book.model.BookInfo;
import com.goottflix.book.model.NfcRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CardMapper {

    int countCardByUid(String cardId);

    @Select("SELECT COUNT(*) FROM cards WHERE card_id = #{cardId} AND user_id = #{userId}")
    int countBookByUid(String cardId,Long userId);

    void saveCardId(String cardId);

    @Update("UPDATE cards SET user_id = #{userId} WHERE card_id = #{cardId}")
    void registerCard(String cardId, Long userId);

    @Update("UPDATE cards SET entered = 'entered' WHERE card_id = #{cardId}")
    void enteredMovie(String cardId);

    BookInfo findBookInfo(String uid);

}
