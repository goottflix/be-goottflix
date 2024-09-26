package com.goottflix.user.service;

import com.goottflix.user.model.User;
import com.goottflix.user.model.UserDTO;
import com.goottflix.user.model.repository.UserMapper;
import com.goottflix.user.social.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;

        if(registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if(registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        } else if(registrationId.equals("kakao")) {
            oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String oauthId = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        System.out.println("oauthId = " + oauthId);

        User existData = userMapper.findByOauthId(oauthId);


        if(existData == null) {
            User user = new User();
            user.setOauthId(oauthId);
            user.setEmail(oAuth2Response.getEmail());
            user.setUsername(oAuth2Response.getName());
            user.setRole("ROLE_USER");
            userMapper.joinIn(user);

            UserDTO userDTO = new UserDTO();
            userDTO.setOauthId(oauthId);
            userDTO.setUsername(oAuth2Response.getName());
            userDTO.setRole("ROLE_USER");
            userDTO.setUserId(user.getId());

            return new CustomOAuth2User(userDTO);

        } else {
            existData.setUsername(oAuth2Response.getName());
            existData.setEmail(oAuth2Response.getEmail());

            userMapper.updateUser(existData);

            UserDTO userDTO = new UserDTO();
            userDTO.setOauthId(oauthId);
            userDTO.setUsername(oAuth2Response.getName()); //db에 있는 username이고
            // 밑에 setName은 오류에서 : principalName은 OAuth2 인증 후에 사용자를 식별하는 데 필요한 값으로, 반드시 값이 있어야 합니다. 에서 필요한 네임이다.(현재 넣어둔것은 oauthId를 넣어두었다.)
            userDTO.setName(oAuth2Response.getName());
            userDTO.setRole(existData.getRole());
            userDTO.setUserId(existData.getId());
            userMapper.setUpdateLastLogin(existData.getId());

            return new CustomOAuth2User(userDTO);
        }

    }

}
