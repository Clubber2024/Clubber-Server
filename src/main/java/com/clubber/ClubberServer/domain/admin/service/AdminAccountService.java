package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsProfileResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordResponse;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import com.clubber.ClubberServer.global.event.withdraw.SoftDeleteEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminAccountService {

	private final AdminReadService adminReadService;
	private final AdminValidator adminValidator;
	private final PasswordEncoder passwordEncoder;
	private final SoftDeleteEventPublisher eventPublisher;

	@Transactional(readOnly = true)
	public GetAdminsProfileResponse getAdminsProfile() {
		Admin admin = adminReadService.getAdmin();
		return GetAdminsProfileResponse.from(admin);
	}

	public UpdateAdminsPasswordResponse updateAdminsPassword(
		UpdateAdminsPasswordRequest updateAdminsPasswordRequest) {
		Admin admin = adminReadService.getAdmin();

		String rawPassword = updateAdminsPasswordRequest.getPassword();
		adminValidator.validateEqualsWithExistPassword(rawPassword, admin.getPassword());

		admin.updatePassword(passwordEncoder.encode(rawPassword));
		return UpdateAdminsPasswordResponse.of(admin);
	}

	public void withDraw() {
		Admin admin = adminReadService.getAdmin();
		admin.withDraw();
		eventPublisher.throwSoftDeleteEvent(admin.getId());
	}
}
