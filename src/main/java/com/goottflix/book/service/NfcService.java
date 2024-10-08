package com.goottflix.book.service;

import com.goottflix.book.model.repository.CardMapper;
import com.goottflix.user.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class NfcService {

    private final Sinks.Many<String> sink;
    private final CardService cardService;

    public NfcService(CardService cardService, JWTUtil jWTUtil, CardMapper cardMapper) {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.cardService = cardService;
    }

    // NFC 데이터를 전송하는 메서드
    public void sendNfcData(String nfcData) {
        String uidValue = extractUid(nfcData);
        System.out.println("uidValue = " + uidValue);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            username = userDetails.getUsername();  // 사용자 이름을 가져옵니다.
            System.out.println("username = " + username);
            System.out.println("userDetails = " + userDetails);
        }


        if (uidValue == null || uidValue.isEmpty() || uidValue.equals("Hello!")) {
            sink.tryEmitNext("Invalid UID received.");
            return;
        }

        if (cardService.isCardRegistered(uidValue)) {
            sink.tryEmitNext(uidValue);
        } else {
            cardService.saveCard(uidValue);
            sink.tryEmitNext(uidValue);
        }

//        sink.tryEmitNext(nfcData);
    }

    private String extractUid(String nfcData) {
        // 여러 줄로 구분하여 첫 번째 줄만 가져옴
        String[] lines = nfcData.split("\n");
        if (lines.length > 0) {
            String firstLine = lines[0].trim();  // 첫 번째 줄 가져오기
            if (!firstLine.isEmpty()) {
                return firstLine;  // 첫 번째 줄 반환
            }
        }
        // 첫 번째 줄이 없는 경우
        return null;
    }

}
