package com.clubber.domain.auth.service;

import com.clubber.domain.auth.dto.KakaoOauthResponse;
import com.clubber.domain.auth.implement.UserTokenAppender;
import com.clubber.domain.auth.implement.UserTokenReader;
import com.clubber.global.jwt.vo.TokenVO;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.domain.user.repository.UserRepository;
import com.clubber.domain.user.implement.UserReader;
import com.clubber.global.config.security.SecurityUtils;
import com.clubber.global.infrastructure.outer.api.oauth.kakao.dto.KakaoUserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final UserReader userReader;
	private final UserTokenAppender userTokenAppender;
	private final UserTokenReader userTokenReader;

	@Transactional
	public KakaoOauthResponse loginOrSignUp(KakaoUserInfoResponse kakaoUserInfoResponse) {
		User user = userRepository.findUserBySnsId(kakaoUserInfoResponse.id())
				.orElseGet(() -> createKakaoUser(kakaoUserInfoResponse.toEntity()));
		TokenVO tokenVO = userTokenAppender.saveUserToken(user);
		return KakaoOauthResponse.of(user, tokenVO.accessToken(), tokenVO.refreshToken());
	}

	public User createKakaoUser(User user) {
		log.info("[회원가입 (카카오) id] : {}", user.getId());
		return userRepository.save(user);
	}

	@Transactional
	public KakaoOauthResponse tokenRefresh(String refreshToken) {
		log.info("[토큰 재발급] : {}", refreshToken);
		Long id = userTokenReader.parseRefreshTokenId(refreshToken);
		User user = userReader.getUserById(id);
		TokenVO tokenVO = userTokenAppender.saveUserToken(user);
		return KakaoOauthResponse.of(user, tokenVO.accessToken(), tokenVO.refreshToken());
	}

	@Transactional
	public void logoutKakaoUser() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		userTokenAppender.deleteRefreshTokenById(currentUserId);
	}

	@Transactional
	public void deleteKakaoUser(User user) {
		log.info("[회원 탈퇴 id] : {}", user.getId());
		user.withDraw();
		userTokenAppender.deleteRefreshTokenById(user.getId());
	}
}
