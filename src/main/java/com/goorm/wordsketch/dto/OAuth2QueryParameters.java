package com.goorm.wordsketch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2QueryParameters {
    private String clientId;
    private String redirectUri;
    private String responseType;
}
