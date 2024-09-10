package com.goottflix.movie.Service;

import com.goottflix.model.Member;
import com.goottflix.movie.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;

    public void save(Member member){
        if(member.getId()==null){
            memberMapper.save(member);
        }else{
            memberMapper.update(member);
        }
    }

    public Member getMemberById(Long id){
        return memberMapper.findById(id);
    }

    public Member getMemberByEmail(String email){
        return memberMapper.findByEmail(email);
    }
}
