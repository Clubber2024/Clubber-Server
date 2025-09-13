package com.clubber.domain.admin.implement;

import static com.clubber.domain.domains.user.domain.AccountState.ACTIVE;

import com.clubber.domain.admin.domain.Admin;
import com.clubber.domain.domains.admin.exception.AdminLoginFailedException;
import com.clubber.domain.domains.admin.exception.AdminNotFoundException;
import com.clubber.domain.domains.admin.exception.AdminUsernameNotFoundException;
import com.clubber.domain.admin.repository.AdminRepository;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.user.domain.AccountState;
import com.clubber.global.config.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminReader {

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

    public Admin getAdminByClub(Club club) {
        return adminRepository.findByClubAndAccountState(club, ACTIVE)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
    }
	public boolean existsByEmailAndClubId(String email, Long clubId) {
		return adminRepository.existsByEmailAndClubIdAndAccountState(email, clubId, ACTIVE);
	}

	public boolean existsByUsername(String username){
		return adminRepository.existsByUsernameAndAccountState(username, AccountState.ACTIVE);
	}
}
