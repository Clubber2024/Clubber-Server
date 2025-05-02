package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.admin.impl.TokenAppender;
import com.clubber.ClubberServer.domain.admin.impl.TokenReader;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import com.clubber.ClubberServer.domain.auth.vo.TokenVO;
import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import com.clubber.ClubberServer.domain.user.exception.RefreshTokenExpiredException;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

	private final AdminReadService adminReadService;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final AdminValidator adminValidator;
	private final TokenAppender tokenAppender;
	private final TokenReader tokenReader;

	@Transactional
	public CreateAdminsLoginResponse createAdminsLogin(CreateAdminsLoginRequest loginRequest) {
		Admin admin = adminReadService.getAdminByUsernameInLogin(loginRequest.getUsername());
		adminValidator.validatePasswordInLogin(loginRequest.getPassword(), admin.getPassword());
		TokenVO tokenVO = tokenAppender.createAdminsToken(admin);
		return CreateAdminsLoginResponse.of(admin, tokenVO.accessToken(), tokenVO.refreshToken());
	}

	private CreateAdminsLoginResponse createAdminsToken(Admin admin) {
		String accessToken = jwtTokenProvider.generateAccessToken(admin);
		String refreshToken = jwtTokenProvider.generateRefreshToken(admin.getId());
		RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.of(admin.getId(), refreshToken,
			jwtTokenProvider.getRefreshTokenTTlSecond());
		RefreshTokenEntity savedRefreshToken = refreshTokenRepository.save(refreshTokenEntity);
		return CreateAdminsLoginResponse.of(admin, accessToken,
			savedRefreshToken.getRefreshToken());
	}

	@Transactional
	public CreateAdminsLoginResponse getAdminsParseToken(String refreshToken) {
		RefreshTokenEntity refreshTokenEntity = tokenReader.getRefreshToken(refreshToken);
		Long adminId = jwtTokenProvider.parseRefreshToken(refreshTokenEntity.getRefreshToken());
		Admin admin = adminReadService.getAdminById(adminId);
		TokenVO tokenVO = tokenAppender.createAdminsToken(admin);
		return CreateAdminsLoginResponse.of(admin, tokenVO.accessToken(), tokenVO.refreshToken());
	}

	@Transactional
	public void logout() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		refreshTokenRepository.deleteById(currentUserId);
	}
}
