package com.goottflix.user.social.dto;

import com.goottflix.user.model.User;
import com.goottflix.user.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {

    private final UserDTO userDTO;

    @Override
    public Map<String, Object> getAttributes() {

        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDTO.getRole();
            }
        });

        return collection;
    }


    // 이 Override부분이 principalName 이고, OAuth2 인증 후에 사용자를 식별하는 데 필요한 값으로, 반드시 값이 있어야 한다.
    // 나는 그 값을 OauthId를 주었고
    // OauthId는 String oauthId = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
    // 로 이루어져있다.
    @Override
    public String getName() {
        return userDTO.getOauthId();
    }

    public String getUsername() {
        return userDTO.getUsername();
    }

    public Long getUserId() {
        return userDTO.getUserId();
    }

}
