package com.clubber.ClubberServer.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.domain.user.domain.AccountState;
import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.exception.RefreshTokenExpiredException;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.kakao.KakaoUserInfoResponse;
import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	@Transactional
	public User loginOrSignUp(KakaoUserInfoResponse kakaoUserInfoResponse) {
		return userRepository.findUserBySnsId(kakaoUserInfoResponse.getId())
			.orElseGet(() -> createKakaoUser(kakaoUserInfoResponse));
	}

	public User createKakaoUser(KakaoUserInfoResponse kakaoUserInfoResponse) {
		User user = kakaoUserInfoResponse.toEntity();
		return userRepository.save(user);
	}

	@Transactional
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

	@Transactional
	public KakaoOauthResponse tokenRefresh(String refreshToken) {
		RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByRefreshToken(
				refreshToken)
			.orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);
		Long id = jwtTokenProvider.parseRefreshToken(refreshTokenEntity.getRefreshToken());
		User user = userRepository.findByIdAndAccountState(id, AccountState.ACTIVE)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);
		return generateUserToken(user);
	}

	@Transactional
	public void logoutKakaoUser() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		refreshTokenRepository.deleteById(currentUserId);
	}

	@Transactional
	public User deleteKakaoUser(User user) {
		user.deleteFavorites();
		user.delete();
		refreshTokenRepository.deleteById(user.getId());
		return user;
	}
}
