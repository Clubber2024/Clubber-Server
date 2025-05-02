package com.clubber.ClubberServer.domain.auth.service;

import com.clubber.ClubberServer.domain.admin.impl.TokenAppender;
import com.clubber.ClubberServer.domain.admin.impl.TokenReader;
import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.domain.user.domain.AccountState;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.kakao.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final JwtTokenService jwtTokenService;
	private final TokenReader tokenReader;
	private final TokenAppender tokenAppender;

	@Transactional
	public User loginOrSignUp(KakaoUserInfoResponse kakaoUserInfoResponse) {
		return userRepository.findUserBySnsId(kakaoUserInfoResponse.getId())
			.orElseGet(() -> createKakaoUser(kakaoUserInfoResponse));
	}

	public User createKakaoUser(KakaoUserInfoResponse kakaoUserInfoResponse) {
		User user = kakaoUserInfoResponse.toEntity();
		log.info("[회원가입 (카카오) id] : {}", user.getId());
		return userRepository.save(user);
	}

	@Transactional
	public KakaoOauthResponse tokenRefresh(String refreshToken) {
		log.info("[토큰 재발급] : {}", refreshToken);
		Long id = tokenReader.parseRefreshTokenId(refreshToken);
		User user = userRepository.findByIdAndAccountState(id, AccountState.ACTIVE)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);
		return jwtTokenService.generateUserToken(user);
	}

	@Transactional
	public void logoutKakaoUser() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		tokenAppender.deleteRefreshTokenById(currentUserId);
	}

	@Transactional
	public void deleteKakaoUser(User user) {
		log.info("[회원 탈퇴 id] : {}", user.getId());
		user.deleteFavorites();
		user.delete();
		tokenAppender.deleteRefreshTokenById(user.getId());
	}
}
