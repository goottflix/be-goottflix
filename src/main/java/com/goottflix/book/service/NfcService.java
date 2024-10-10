package com.goottflix.book.service;

import com.goottflix.book.model.repository.CardMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class NfcService {

    private final Sinks.Many<String> sink;
    private final CardService cardService;

    public NfcService(CardService cardService) {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
        this.cardService = cardService;
    }

    // NFC 데이터를 처리하는 메서드
    public void sendNfcData(String uidValue) {
        System.out.println("Received UID: " + uidValue);

        if (uidValue == null || uidValue.isEmpty()) {
            sink.tryEmitNext("Invalid UID received.");
            return;
        }

        // 카드가 등록되었는지 확인하고 처리
        if (cardService.isCardRegistered(uidValue)) {
            sink.tryEmitNext("Card already registered: " + uidValue);
        } else {
            cardService.saveCard(uidValue);
            sink.tryEmitNext("New card registered: " + uidValue);
        }
    }
}
