package com.goottflix.controller;

import com.goottflix.model.Member;
import com.goottflix.movie.Service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/create")
    public String create(){
        return "memberCreate";
    }

    @PostMapping("/member/create")
    public String createPost(Member member){
        memberService.save(member);

        return "redirect:/member/login";
    }

    @GetMapping("/member/login")
    public String login(){
        return "memberLogin";
    }

    @PostMapping("/member/login")
    public String loginPost(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session){
        Member member = memberService.getMemberByEmail(email);

        if(member!=null && member.getPassword().equals(password)){
            session.setAttribute("member", member);
            return "redirect:/movie/list";
        }else{
            return "redirect:/member/login";
        }

    }
}
