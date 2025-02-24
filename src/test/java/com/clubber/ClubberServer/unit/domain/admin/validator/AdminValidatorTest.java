package com.clubber.ClubberServer.unit.domain.admin.validator;

import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AdminValidatorTest {

	@InjectMocks
	private AdminValidator adminValidator;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("비밀번호 검증에 성공시 예외가 발생하지 않는다.")
	public void validatePasswordTest() {
		//given
		String password = "password";
		String encodedPassword = "encodedPassword";
		Mockito.when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

		//when & Then
		Assertions.assertThatCode(() -> adminValidator.validatePassword(password, encodedPassword))
			.doesNotThrowAnyException();
	}
}
