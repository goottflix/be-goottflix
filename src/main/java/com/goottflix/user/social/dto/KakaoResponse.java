package com.goottflix.user.social.dto;

import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;

    public KakaoResponse(Map<String, Object> attribute) {
        this.attribute = attribute;
    }


    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {

        //카카오에서는 개발단계에서 email정보를 주지 않기 때문에 null 처리
        return null; 
    }

    @Override
    public String getName() {
        // "kakao_account" 안의 "profile"에 "nickname"이 존재
        Map<String, Object> kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        if (kakaoAccount != null) {
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            if (profile != null && profile.get("nickname") != null) {
                return profile.get("nickname").toString();
            }
        }
        return null;  // 이름이 없을 경우 null 처리
    }
}
