package com.clubber.ClubberServer.domain.auth.service;


import static com.clubber.ClubberServer.global.jwt.JwtStatic.BEARER;

import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.domain.auth.dto.UnlinkKaKaoTarget;
import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import com.clubber.ClubberServer.domain.user.domain.SnsType;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.domain.AccountState;
import com.clubber.ClubberServer.domain.user.exception.RefreshTokenExpiredException;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.KakaoInfoClient;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.KakaoOauthClient;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoUserInfoResponse;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.properties.KakaoProperties;
import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final KakaoOauthClient kakaoOauthClient;

    private final KakaoInfoClient kakaoInfoClient;

    private final KakaoProperties kakaoProperties;

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    public KakaoTokenResponse getToken(String code){

        return kakaoOauthClient.kakaoAuth(
                kakaoProperties.getClientId(),
                kakaoProperties.getRedirectUrl(),
                code);
    }

    public KakaoUserInfoResponse getUserKakaoInfo(String accessToken){
        return kakaoInfoClient.getUserInfo(BEARER + accessToken);
    }

    @Transactional
    public User loginOrSignUp(KakaoUserInfoResponse kakaoUserInfoResponse){
        return userRepository.findUserBySnsId(kakaoUserInfoResponse.getId())
                .orElseGet(() -> createKakaoUser(kakaoUserInfoResponse));
    }

    public User createKakaoUser(KakaoUserInfoResponse kakaoUserInfoResponse){
        User user = User.builder()
                .snsId(kakaoUserInfoResponse.getId())
                .email(kakaoUserInfoResponse.getEmail())
                .snsType(SnsType.KAKAO)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public KakaoOauthResponse generateUserToken(User user){
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());
        saveRefreshTokenEntity(user, refreshToken);
        return KakaoOauthResponse.of(user, accessToken, refreshToken);
    }

    private void saveRefreshTokenEntity(User user, String refreshToken) {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.of(user.getId(), refreshToken,
                jwtTokenProvider.getRefreshTokenTTlSecond());
        refreshTokenRepository.save(refreshTokenEntity);
    }

    @Transactional
    public KakaoOauthResponse tokenRefresh(String refreshToken){
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByRefreshToken(
                        refreshToken)
                .orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);
        Long id = jwtTokenProvider.parseRefreshToken(refreshTokenEntity.getRefreshToken());
        User user = userRepository.findByIdAndAccountState(id, AccountState.ACTIVE)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        return generateUserToken(user);
    }

    @Transactional
    public void logoutKakaoUser(){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        refreshTokenRepository.deleteById(currentUserId);
    }

    @Transactional
    public void withDrawKakaoUser(){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        unlinkKakao(user);
        refreshTokenRepository.deleteById(user.getId());
        user.withDraw(); 
    }

    private void unlinkKakao(User user){
        String header = "KakaoAK " + kakaoProperties.getAdminKey();
        UnlinkKaKaoTarget unlinkKakaoTarget = UnlinkKaKaoTarget.from(user.getSnsId());
        kakaoInfoClient.unlink(header, unlinkKakaoTarget);
    }
}
