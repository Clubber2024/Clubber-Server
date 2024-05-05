package com.clubber.ClubberServer.domain.auth.dto;


import com.clubber.ClubberServer.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class KakaoOauthResponse {
    private final Long userId;
    private final String accessToken;
    private final String refreshToken;

    public static KakaoOauthResponse of(User user, String accessToken, String refreshToken){
        return KakaoOauthResponse.builder()
                .userId(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
