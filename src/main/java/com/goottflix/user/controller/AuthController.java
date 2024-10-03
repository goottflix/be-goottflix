package com.goottflix.user.controller;

import com.goottflix.user.service.MailService;
import com.goottflix.user.service.VericiationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final MailService mailService;

    private final VericiationService vericiationService;

    //인증번호 발송
    @PostMapping("/sendCode")
    public ResponseEntity<String> sendVerificationCode(@RequestParam("email") String email) {

        System.out.println("가입한 이메일주소 = " + email);
        try {
            String verificationCode = mailService.sendVerificationCode(email);
            vericiationService.saveVerificationCode(email, verificationCode);
            return ResponseEntity.ok("인증번호 발송 완료.");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("인증번호 발송 중 오류가 발생했습니다.");
        }
    }


    //인증번호 확인
    @PostMapping("/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestParam String email,
                                             @RequestParam String code) {
        if(vericiationService.verifyCode(email, code)) {
            vericiationService.removeVerificationCode(email); // 인증번호 삭제
            return ResponseEntity.ok("인증 완료");
        } else {
            return ResponseEntity.badRequest().body("인증번호가 잘못되었습니다.");
        }
    }
}
