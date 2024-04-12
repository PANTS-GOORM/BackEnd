package com.goorm.wordsketch.service;

import com.goorm.wordsketch.entity.User;
import com.goorm.wordsketch.entity.UserRole;
import com.goorm.wordsketch.entity.UserSocialType;
import com.goorm.wordsketch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest = " + userRequest);

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 서비스 구분을 위한 작업 (구글: google, 깃허브: github, 카카오: kakao)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        String email;
        Map<String, Object> response = oAuth2User.getAttributes();

        System.out.println("response = " + response);
        if (registrationId.equals("github")) {
            email = (String) response.get("url");
        } else if (registrationId.equals("kakao")) {
            Map<String, Object> hash = (Map<String, Object>) response.get("kakao_account");
            System.out.println("hash = " + hash);
            email = (String) hash.get("email");
        } else {
            throw new OAuth2AuthenticationException("허용되지 않는 인증입니다.");
        }

        // 등록된 유저면 등록 정보 가져오고, 아니면 유저 정보 등록
        User user;
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = new User();
            if (registrationId.equals("github")) {
                user = user.builder()
                        .email(email)
                        .socialType(UserSocialType.valueOf(registrationId.toUpperCase()))
                        .nickname((String) response.get("login"))
                        .profileImg((String) response.get("avatar_url"))
                        .role(UserRole.USER)
                        .isAdmin(false)
                        .build();
            } else if (registrationId.equals("kakao")) {
                Map<String, Object> hash = (Map<String, Object>) response.get("properties");

                user = user.builder()
                        .email(email)
                        .socialType(UserSocialType.valueOf(registrationId.toUpperCase()))
                        .nickname((String) hash.get("nickname"))
                        .profileImg((String) hash.get("profile_image"))
                        .role(UserRole.USER)
                        .refreshToken("test")
                        .isAdmin(false)
                        .build();
            } else if (registrationId.equals("google")) {

            }
        }
        userRepository.save(user);

        // oAuth2User의 attributes를 복사하여 새로운 맵을 만듭니다.
        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());
        // 새 맵에 사용자 객체를 추가합니다.
        attributes.put("customUser", user);
        attributes.put("email", email);


        System.out.println("userNameAttributeName = " + userNameAttributeName);
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().toString()))
                , attributes
                , "email"
        );
    }
}
