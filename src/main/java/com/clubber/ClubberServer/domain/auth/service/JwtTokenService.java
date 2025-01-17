package com.clubber.ClubberServer.domain.auth.service;

import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

	private final JwtTokenProvider jwtTokenProvider;

	private final RefreshTokenRepository refreshTokenRepository;

	public KakaoOauthResponse generateUserToken(User user) {
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

	public Long parseRefreshToken(RefreshTokenEntity refreshTokenEntity) {
		return jwtTokenProvider.parseRefreshToken(refreshTokenEntity.getRefreshToken());
	}
}
