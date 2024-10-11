package com.goottflix.book.controller;

import com.goottflix.book.model.WifiCredentials;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wifi")
public class WifiController {

    private String ssid ;
    private String password ;

    // GET 요청: 저장된 SSID와 Password 제공
    @GetMapping("/credentials")
    public ResponseEntity<WifiCredentials> getWifiCredentials() {
        WifiCredentials credentials = new WifiCredentials();
        credentials.setSsid(ssid);
        credentials.setPassword(password);

        return ResponseEntity.ok(credentials);
    }

    // POST 요청: 새로운 SSID와 Password 입력 받기
    @PostMapping("/connect")
    public ResponseEntity<String> connectToWifi(@RequestBody WifiCredentials credentials) {
        this.ssid = credentials.getSsid();
        this.password = credentials.getPassword();
        System.out.println("Received SSID: " + ssid);
        System.out.println("Received Password: " + password);

        return ResponseEntity.ok("WiFi credentials updated: SSID = " + ssid + ", Password = " + password);
    }
}


