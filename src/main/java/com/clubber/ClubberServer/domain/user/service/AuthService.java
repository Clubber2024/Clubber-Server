package com.clubber.ClubberServer.domain.user.service;


import static com.clubber.ClubberServer.global.jwt.JwtProperties.BEARER;

import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.KakaoOauthClient;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoInformationResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoUserInfoResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.properties.KakaoProperties;
import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoOauthClient kakaoOauthClient;

    private final KakaoProperties kakaoProperties;

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public KakaoTokenResponse getToken(String code){

        return kakaoOauthClient.kakaoAuth(
                URI.create(kakaoProperties.getTokenUrl()),
                kakaoProperties.getGrantType(),
                kakaoProperties.getClientId(),
                kakaoProperties.getRedirectUrl(),
                code);
    }

    public KakaoUserInfoResponse getUserKakaoInfo(String accessToken){
        return kakaoOauthClient.getUserInfo(
                URI.create(kakaoProperties.getUserInfoUrl()),
                BEARER + accessToken
        );
    }

    public User loginOrSignUp(Long snsId){
        return userRepository.findUserBySnsId(snsId).orElseGet(() -> createKakaoUser(snsId));
    }

    public User createKakaoUser(Long snsId){
        User user = User.builder()
                .snsId(snsId)
                .role("user")
                .snsType("kakao")
                .build();

        return userRepository.save(user);
    }
}
