package com.clubber.ClubberServer.domain.admin.validator;

import com.clubber.ClubberServer.domain.admin.exception.AdminEqualsPreviousPasswordExcpetion;
import com.clubber.ClubberServer.domain.admin.exception.AdminInvalidAuthCodeException;
import com.clubber.ClubberServer.domain.admin.exception.AdminLoginFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminValidator {

	private final PasswordEncoder encoder;

	public void validatePassword(String rawPassword, String encodedPassword) {
		if (!encoder.matches(rawPassword, encodedPassword)) {
			throw AdminLoginFailedException.EXCEPTION;
		}
	}

	public void validateEqualsWithExistPassword(String rawPassword, String encodedPassword) {
		if (encoder.matches(rawPassword, encodedPassword)) {
			throw AdminEqualsPreviousPasswordExcpetion.EXCEPTION;
		}
	}

	public void validateAuthString(String requestAuthString, String savedAuthString) {
		if (!requestAuthString.equals(savedAuthString)) {
			throw AdminInvalidAuthCodeException.EXCEPTION;
		}
	}
}
