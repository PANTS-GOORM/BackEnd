package com.goorm.wordsketch.config;

import com.goorm.wordsketch.service.CustomOAuth2UserService;
import com.goorm.wordsketch.util.JwtTokenValidatorFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private AuthenticationSuccessHandler oauth2LoginSuccessHandler;

    @Autowired
    private JwtTokenValidatorFilter jwtTokenValidatorFilter;

    @Value("${jwt.access.cookie}")
    private String accessCookie;

    @Value("${jwt.refresh.cookie}")
    private String refreshCookie;

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Collections.singletonList(accessCookie));
                        config.setExposedHeaders(Collections.singletonList(refreshCookie));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtTokenValidatorFilter, BasicAuthenticationFilter.class)

                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "login/oauth2/**", "/oauth2/**", "favicon.ico").permitAll()
                        .requestMatchers("/user").hasRole("User")
                        .anyRequest().authenticated())

                .oauth2Login(oauth2 -> oauth2
                        .loginPage("http://localhost:3000/login")
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(oauth2LoginSuccessHandler)
                );
        return http.build();
    }

}
