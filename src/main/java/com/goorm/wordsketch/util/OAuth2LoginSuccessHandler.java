package com.goorm.wordsketch.util;


import com.goorm.wordsketch.entity.User;
import com.goorm.wordsketch.repository.UserRepository;
import com.goorm.wordsketch.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            User customUser = null;
            if (authentication.getPrincipal() instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

                // OAuth2User에서 사용자 속성을 Map으로 가져옵니다.
                Map<String, Object> attributes = oAuth2User.getAttributes();

                // 'customUser' 속성을 사용하여 추가 사용자 정보를 가져올 수 있습니다.
                // 이 부분은 당신의 애플리케이션 로직에 따라 다를 수 있습니다.
                customUser = (User) attributes.get("customUser");

                System.out.println("customUser = " + customUser);
            }

            loginSuccess(response, customUser); // 로그인에 성공한 경우 access, refresh 토큰 생성

        } catch (Exception e) {
            throw e;
        }
    }

    // TODO : 소셜 로그인 시에도 무조건 토큰 생성하지 말고 JWT 인증 필터처럼 RefreshToken 유/무에 따라 다르게 처리해보기
    private void loginSuccess(HttpServletResponse response, User user) throws IOException {
        String accessToken = jwtService.createAccessToken(user.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
        response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        jwtService.updateRefreshToken(user.getEmail(), refreshToken);
    }
}
