package com.goottflix.user.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class VericiationService {

    //인증코드를 해쉬맵을 통해 메모리 저장방식
    private Map<String, String> verificationCodes = new HashMap<>();
    
    public void saveVerificationCode(String email, String code) {
        verificationCodes.put(email, code);
    }
    
    public boolean verifyCode(String email, String code) {
        return code.equals(verificationCodes.get(email));
    }
    
    public void removeVerificationCode(String email) {
        verificationCodes.remove(email);
    }
}
