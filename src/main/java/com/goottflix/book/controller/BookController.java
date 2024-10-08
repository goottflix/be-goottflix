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

