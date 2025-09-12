package com.clubber.domain.admin.implement;

import com.clubber.domain.admin.exception.AdminEqualsPreviousPasswordExcpetion;
import com.clubber.domain.admin.exception.AdminInvalidAuthCodeException;
import com.clubber.domain.admin.exception.AdminInvalidCurrentPasswordException;
import com.clubber.domain.admin.exception.AdminLoginFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminValidator {

	private final PasswordEncoder encoder;

	public void validatePasswordInLogin(String rawPassword, String encodedPassword) {
		if (!encoder.matches(rawPassword, encodedPassword)) {
			throw AdminLoginFailedException.EXCEPTION;
		}
	}

	public void validateExistPassword(String rawPassword, String encodedPassword) {
		if (!encoder.matches(rawPassword, encodedPassword)) {
			throw AdminInvalidCurrentPasswordException.EXCEPTION;
		}
	}

	public void validateEqualsWithExistPassword(String rawPassword, String encodedPassword) {
		if (encoder.matches(rawPassword, encodedPassword)) {
			throw AdminEqualsPreviousPasswordExcpetion.EXCEPTION;
		}
	}

	public void validateAuthCode(Integer requestAuthCode, Integer storedAuthCode) {
		if (!requestAuthCode.equals(storedAuthCode)) {
			throw AdminInvalidAuthCodeException.EXCEPTION;
		}
	}
}
