package com.goottflix.user.controller;

import com.goottflix.user.jwt.JWTUtil;
import com.goottflix.user.model.LoginDTO;
import com.goottflix.user.model.repository.UserMapper;
import com.goottflix.user.service.MailService;
import com.goottflix.user.service.VericiationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final UserMapper userMapper;
    private final JWTUtil jWTUtil;

    //인증번호 발송
    @PostMapping("/sendCode")
    public ResponseEntity<String> sendVerificationCode(@RequestParam("email") String email) {

        boolean exist = userMapper.existsByEmail(email);
        if (exist) {
            return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다");
        }
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

    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestParam("email") String email){
        LoginDTO loginDTO = userMapper.findByEmail(email);
        if (loginDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 이메일입니다.");
        }

        String token = jWTUtil.createPsswordResetJwt(email,360000L); //6분 예상

        try{
            mailService.sendPasswordResetEmail(email, loginDTO.getLoginId(), token);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok("비밀번호 초기화를 위한 메일을 전송했습니다");
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token,
                                                @RequestParam("password") String newPassword) {
        String email = jWTUtil.getEmail(token);
        if(email == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 토큰입니다.");
        }
        LoginDTO loginDTO = userMapper.findByEmail(email);
        if (loginDTO == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 회원입니다.");
        }

        loginDTO.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userMapper.setUserNewPassword(loginDTO);

        return ResponseEntity.ok("비밀번호 성공 완료! 새로운 비밀번호로 로그인해주세요.");

    }
}
