package com.goottflix.user.controller;

import com.goottflix.user.model.JoinDTO;
import com.goottflix.user.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDTO joinDTO) {
        System.out.println("joinDTO.getUsername() = " + joinDTO.getUsername());
        joinService.joinProcess(joinDTO);
        return "ok";
    }


}
