package com.goottflix.book.controller;


import com.goottflix.book.model.BookInfo;
import com.goottflix.book.model.NfcRequest;
import com.goottflix.book.model.repository.CardMapper;
import com.goottflix.book.service.CardService;
import com.goottflix.user.jwt.JWTUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/book")
public class BookController {

    // SSE를 위한 Sinks
    private final Sinks.Many<String> sink;
    private final CardService cardService;
    private final JWTUtil jWTUtil;
    private final CardMapper cardMapper;

    public BookController(CardService cardService, JWTUtil jWTUtil, CardMapper cardMapper) {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.cardService = cardService;
        this.jWTUtil = jWTUtil;
        this.cardMapper = cardMapper;
    }

    // SSE 엔드포인트: 클라이언트에서 /book/nfc-data로 요청하면 실시간 데이터 전송
    @GetMapping(value = "/nfc-data", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamNfcData() {
        return sink.asFlux().delayElements(Duration.ofMillis(100));
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

    @PostMapping("save-card")
    public ResponseEntity<?> registerCard(@CookieValue("Authorization") String token,
                                          @RequestParam String uid) {
        Long userId = jWTUtil.getUserID(token);
        try {
            cardService.registerCard(uid, userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/process-card")
    public ResponseEntity<?> processCard(@CookieValue("Authorization") String token,
                                         @RequestBody NfcRequest nfcRequest) {
        Long userId = jWTUtil.getUserID(token);
        String uid = nfcRequest.getUid();
        String mode = nfcRequest.getMode();

        if ("register".equals(mode)) {
            if (cardService.isCardRegistered(uid)) {
                return ResponseEntity.ok("등록된 카드입니다.");
            } else {
                cardService.saveCard(uid); // 카드 등록
                return ResponseEntity.ok("성공적으로 카드를 등록했습니다.");
            }
        } else if ("validate".equals(mode)) {
            if (cardService.checkBook(uid, userId)) {
                cardService.enterPerson(uid);
                BookInfo bookInfo = cardMapper.findBookInfo(uid);
                System.out.println("bookInfo = " + bookInfo);
                return ResponseEntity.ok(bookInfo);
            }
        } else if ("registerUser".equals(mode)) {
                if (cardService.isCardRegistered(uid)) {
                    cardService.registerCard(uid,userId);
                    return ResponseEntity.ok("카드에 로그인정보를 저장했습니다.");
                }
            } else {
        }
        return ResponseEntity.badRequest().body("사용가능한 카드번호가 아닙니다.");

    }
 }

