package com.clubber.ClubberServer.domain.admin.service;

import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.exception.AdminLoginFailedException;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.exception.AdminUsernameNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminReadService {

	private final AdminRepository adminRepository;

	public Admin getCurrentAdmin() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		return adminRepository.findAdminByIdAndAccountState(currentUserId, ACTIVE)
			.orElseThrow(() -> AdminNotFoundException.EXCEPTION);
	}

	public Admin getAdminByEmail(String email) {
		return adminRepository.findByEmailAndAccountState(email, ACTIVE)
			.orElseThrow(() -> AdminNotFoundException.EXCEPTION);
	}

	public Admin getAdminByUsernameInLogin(String username) {
		return adminRepository.findByUsernameAndAccountState(username, ACTIVE)
			.orElseThrow(() -> AdminLoginFailedException.EXCEPTION);
	}

    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsernameAndAccountState(username, ACTIVE)
                .orElseThrow(() -> AdminUsernameNotFoundException.EXCEPTION);
    }

	public Admin getAdminById(Long id) {
		return adminRepository.findAdminByIdAndAccountState(id, ACTIVE)
			.orElseThrow(() -> AdminNotFoundException.EXCEPTION);
	}

	public Admin getAdminByEmailAndClubId(String email, Long clubId) {
		return adminRepository.findByEmailAndClubIdAndAccountState(email, clubId, ACTIVE)
				.orElseThrow(() -> AdminNotFoundException.EXCEPTION);
	}

	public boolean existsByEmailAndClubId(String email, Long clubId) {
		return adminRepository.existsByEmailAndClubIdAndAccountState(email, clubId, ACTIVE);
	}

    public boolean existsByEmailAndUsername(String email, String username) {
        return adminRepository.existsByEmailAndUsernameAndAccountState(email, username, ACTIVE);
    }
}
