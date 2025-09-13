package com.clubber.domain.auth.dto;


import com.clubber.domain.domains.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class KakaoOauthResponse {

    @Schema(description = "유저 id", example = "1")
    private final Long userId;

    @Schema(description = "액세스 토큰")
    private final String accessToken;

    @Schema(description = "리프레시 토큰")
    private final String refreshToken;

    public static KakaoOauthResponse of(User user, String accessToken, String refreshToken){
        return KakaoOauthResponse.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
