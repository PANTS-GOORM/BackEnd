package com.goorm.wordsketch.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.goorm.wordsketch.entity.User;
import com.goorm.wordsketch.repository.UserRepository;
import com.goorm.wordsketch.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    @Value("${spring.security.oauth2.redirect-uri}")
    private String redirectUrl;

    // Todo: 배포하면 url 변경 필요
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (null != authentication) {
            jwtService.createAccessToken(response, authentication);
            jwtService.createRefreshToken(response, authentication);

            // TODO: 로그 출력
            log.info("email = " + authentication.getName());
            Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
            // TODO: 로그 출력
            log.info("optionalUser = " + optionalUser);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                // TODO: 로그 출력
                log.info("user = " + user);
                String userJson = objectMapper.writeValueAsString(user);
                String encodedUserJson = URLEncoder.encode(userJson, StandardCharsets.UTF_8);
                // TODO: 로그 출력
                log.info("redirectUrl = " + redirectUrl);
                response.sendRedirect(redirectUrl + "?user=" + encodedUserJson);
            } else {
                String errorMessage = URLEncoder.encode("User not found", StandardCharsets.UTF_8);
                // TODO: 로그 출력
                log.info("redirectUrl = " + redirectUrl);
                response.sendRedirect(redirectUrl + "?error=" + errorMessage);
            }
        }

    }
}
