package com.clubber.ClubberServer.domain.auth.service;

import com.clubber.ClubberServer.domain.admin.implement.UserTokenReader;
import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.domain.auth.implement.UserTokenAppender;
import com.clubber.ClubberServer.domain.auth.vo.TokenVO;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.domain.user.service.UserReadService;
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
	private final UserTokenReader userTokenReader;
	private final UserTokenAppender userTokenAppender;
	private final UserReadService userReadService;

	@Transactional
	public KakaoOauthResponse loginOrSignUp(KakaoUserInfoResponse kakaoUserInfoResponse) {
		User user = userRepository.findUserBySnsId(kakaoUserInfoResponse.getId())
				.orElseGet(() -> createKakaoUser(kakaoUserInfoResponse.toEntity()));
		TokenVO tokenVO = userTokenAppender.generateUserToken(user);
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
		User user = userReadService.getUserById(id);
		TokenVO tokenVO = userTokenAppender.generateUserToken(user);
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
		user.deleteFavorites();
		user.delete();
		userTokenAppender.deleteRefreshTokenById(user.getId());
	}
}
