package com.clubber.ClubberServer.domain.user.implement;

import com.clubber.ClubberServer.domain.user.domain.AccountState;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReader {

	private final UserRepository userRepository;

	public User getUser() {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		return userRepository.findByIdAndAccountState(currentUserId, ACTIVE)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}

	public User getUserById(Long id) {
		return userRepository.findByIdAndAccountState(id, AccountState.ACTIVE)
				.orElseThrow(() -> UserNotFoundException.EXCEPTION);
	}
}
