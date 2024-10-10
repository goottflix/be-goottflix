package com.goottflix.book.controller;

import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wifi")
public class WifiController {

    private String ssid = "defaultSSID";  // 초기값 설정
    private String password = "defaultPassword";  // 초기값 설정

    // GET 요청: 저장된 SSID와 Password 제공
    @GetMapping("/credentials")
    public ResponseEntity<WifiCredentials> getWifiCredentials() {
        WifiCredentials credentials = new WifiCredentials();
        credentials.setSsid(ssid);  // 저장된 SSID 반환
        credentials.setPassword(password);  // 저장된 Password 반환

        return ResponseEntity.ok(credentials);
    }

    // POST 요청: 새로운 SSID와 Password 입력 받기
    @PostMapping("/connect")
    public ResponseEntity<String> connectToWifi(@RequestBody WifiCredentials credentials) {
        // 전달받은 SSID와 Password를 저장
        this.ssid = credentials.getSsid();
        this.password = credentials.getPassword();

        System.out.println("Received SSID: " + ssid);
        System.out.println("Received Password: " + password);

        // 응답으로 받은 SSID와 Password를 확인 메시지로 반환
        return ResponseEntity.ok("WiFi credentials updated: SSID = " + ssid + ", Password = " + password);
    }
}

@Data
class WifiCredentials {
    private String ssid;
    private String password;
}
