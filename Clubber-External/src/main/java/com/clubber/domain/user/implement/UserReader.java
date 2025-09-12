package com.clubber.domain.user.implement;

import com.clubber.domain.domains.user.domain.AccountState;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.domain.domains.user.exception.UserNotFoundException;
import com.clubber.domain.user.repository.UserRepository;
import com.clubber.global.config.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.clubber.domain.domains.user.domain.AccountState.ACTIVE;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReader {

	private final UserRepository userRepository;

	public User getCurrentUser() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		return userRepository.findByIdAndAccountState(currentUserId, ACTIVE)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}

	public User getUserById(Long id) {
		return userRepository.findByIdAndAccountState(id, AccountState.ACTIVE)
				.orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}
}
