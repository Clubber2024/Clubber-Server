package com.clubber.global.infrastructure.outer.api.oauth.kakao.dto;

import feign.form.FormProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoOAuthRequest {
    @FormProperty("client_id")
    private String clientId;
    @FormProperty("redirect_uri")
    private String redirectUrl;
    @FormProperty("code")
    private String code;
}
