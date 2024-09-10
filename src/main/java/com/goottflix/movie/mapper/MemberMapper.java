package com.goottflix.movie.mapper;

import com.goottflix.model.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Member findById(Long id);

    Member findByEmail(String email);

    void save(Member member);

    void update(Member member);
}
