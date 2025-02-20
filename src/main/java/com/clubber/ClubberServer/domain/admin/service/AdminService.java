package com.clubber.ClubberServer.domain.admin.service;

import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.AdminEmailAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminMailAuthRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsProfileResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageResponse;
import com.clubber.ClubberServer.domain.admin.exception.AdminEqualsPreviousPasswordExcpetion;
import com.clubber.ClubberServer.domain.admin.exception.AdminLoginFailedException;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminEmailAuthRepository;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import com.clubber.ClubberServer.domain.club.dto.GetClubInfoResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubResponse;
import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import com.clubber.ClubberServer.domain.user.exception.RefreshTokenExpiredException;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import com.clubber.ClubberServer.global.util.RandomAuthStringGeneratorUtil;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.global.event.withdraw.SoftDeleteEventPublisher;
import com.clubber.ClubberServer.global.infrastructure.outer.mail.MailService;
import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
import com.clubber.ClubberServer.global.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final AdminRepository adminRepository;
	private final AdminReadService adminReadService;
	private final PasswordEncoder encoder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;
	private final SoftDeleteEventPublisher eventPublisher;
	private final RandomAuthStringGeneratorUtil randomAuthStringGeneratorUtil;
	private final MailService mailService;
	private final AdminEmailAuthRepository adminEmailAuthRepository;

	@Transactional
	public CreateAdminsLoginResponse createAdminsLogin(CreateAdminsLoginRequest loginRequest) {
		Admin admin = adminRepository.findByUsernameAndAccountState(loginRequest.getUsername(),
				ACTIVE)
			.orElseThrow(() -> AdminLoginFailedException.EXCEPTION);

		validatePassword(loginRequest.getPassword(), admin.getPassword());
		return createAdminsToken(admin);
	}

	private void validatePassword(String rawPassword, String encodedPassword) {
		if (!encoder.matches(rawPassword, encodedPassword)) {
			throw AdminLoginFailedException.EXCEPTION;
		}
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
	public void createAdminMailAuth(AdminEmailAuth adminEmailAuth) {
		adminEmailAuthRepository.save(adminEmailAuth);
	}

	@Transactional(readOnly = true)
	public GetAdminsProfileResponse getAdminsProfile() {
		Admin admin = adminReadService.getAdmin();
		return GetAdminsProfileResponse.from(admin);
	}

	@Transactional
	public UpdateAdminsPasswordResponse updateAdminsPassword(
		UpdateAdminsPasswordRequest updateAdminsPasswordRequest) {
		Admin admin = adminReadService.getAdmin();

		String rawPassword = updateAdminsPasswordRequest.getPassword();

		if (encoder.matches(rawPassword, admin.getPassword())) {
			throw AdminEqualsPreviousPasswordExcpetion.EXCEPTION;
		}

		admin.updatePassword(encoder.encode(rawPassword));
		return UpdateAdminsPasswordResponse.of(admin);
	}

	@Transactional
	public void logout() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		refreshTokenRepository.deleteById(currentUserId);
	}

	@Transactional
	public CreateAdminsLoginResponse getAdminsParseToken(String refreshToken) {
		RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByRefreshToken(
				refreshToken)
			.orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);
		Long adminId = jwtTokenProvider.parseRefreshToken(refreshTokenEntity.getRefreshToken());
		Admin admin = adminRepository.findAdminByIdAndAccountState(adminId, ACTIVE)
			.orElseThrow(() -> AdminNotFoundException.EXCEPTION);
		return createAdminsToken(admin);
	}

	@Transactional
	public UpdateClubPageResponse updateAdminsPage(UpdateClubPageRequest updateClubPageRequest) {
		Admin admin = adminReadService.getAdmin();

		Club club = admin.getClub();

		String imageKey = ImageUtil.parseImageKey(updateClubPageRequest.getImageKey());
		club.updateClub(imageKey, updateClubPageRequest.getIntroduction());

		ClubInfo clubinfo = club.getClubInfo();
		clubinfo.updateClubInfo(updateClubPageRequest.getInstagram(),
			updateClubPageRequest.getLeader(), updateClubPageRequest.getActivity(),
			updateClubPageRequest.getRoom());

		return UpdateClubPageResponse.of(club, clubinfo);
	}

	public GetClubResponse getAdminsMyPage() {
		Admin admin = adminReadService.getAdmin();
		Club club = admin.getClub();
		return GetClubResponse.of(club, GetClubInfoResponse.from(club.getClubInfo()));
	}

	@Transactional
	public void withDraw() {
		Admin admin = adminReadService.getAdmin();
		admin.withDraw();
		eventPublisher.throwSoftDeleteEvent(admin.getId());
	}
}
