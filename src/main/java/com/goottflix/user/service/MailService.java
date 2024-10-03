package com.goottflix.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public String sendVerificationCode(String email) throws MessagingException {

        // 6자리 인증번호 생성
        String verificationCode = String.format("%06d", new Random().nextInt(1000000));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        System.out.println("여기는 MailService입니다. email = " + email);

        helper.setTo(email);
        helper.setSubject("구트플릭스 회원가입을 위한 인증번호입니다.");

        String htmlContent = "<div style='text-align: center; font-family: Arial, sans-serif;'>"
                + "<h2>확인 코드</h2>"
                + "<h1 style='font-size: 48px; color: #333;'>" + verificationCode + "</h1>"
                + "<p style='color: #666;'>이 코드는 전송 10분 후에 만료됩니다.</p>"
                + "<hr style='margin: 20px 0;'>"
                + "<p style='font-size: 14px; color: #666;'>gootFlix Services는 절대 사용자 암호, 신용카드 또는 은행 계좌 번호를 요구하지 않습니다...</p>"
                + "</div>";

        helper.setText(htmlContent, true);  // 두 번째 매개변수는 HTML 설정
        mailSender.send(message);

        return verificationCode;
    }
}
