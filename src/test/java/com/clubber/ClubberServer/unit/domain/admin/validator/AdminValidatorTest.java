package com.clubber.ClubberServer.unit.domain.admin.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.clubber.ClubberServer.domain.admin.exception.AdminEqualsPreviousPasswordExcpetion;
import com.clubber.ClubberServer.domain.admin.exception.AdminInvalidAuthCodeException;
import com.clubber.ClubberServer.domain.admin.exception.AdminLoginFailedException;
import com.clubber.ClubberServer.domain.admin.implement.AdminValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AdminValidatorTest {

    @InjectMocks
    private AdminValidator adminValidator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("비밀번호가 같을 시 예외가 발생하지 않는다.")
    public void validatePasswordInLoginSuccessTest() {
        //given
        String password = "password";
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        //when & Then
        assertThatCode(() -> adminValidator.validatePasswordInLogin(password, encodedPassword))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("비밀번호 다를 시 예외가 발생한다.")
    public void validatePasswordInLoginFailTest() {
        //given
        String password = "password";
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        //when & then
        assertThatThrownBy(() -> adminValidator.validatePasswordInLogin(password, encodedPassword))
                .isInstanceOf(AdminLoginFailedException.class);
    }

    @Test
    @DisplayName("비밀번호 변경시 이전과 다른 비밀번호를 입력하면 예외가 발생하지 않는다.")
    public void validateEqualsWithExistPasswordSuccessTest() {
        //given
        String password = "password";
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        //when & then
        assertThatCode(
                () -> adminValidator.validateEqualsWithExistPassword(password, encodedPassword))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("비밀번호 변경시 이전과 같은 비밀번호를 입력하면 예외가 발생한다.")
    public void validateEqualsWithExistPasswordFailTest() {
        //given
        String password = "password";
        String encodedPassword = "encodedPassword";
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        //when & then
        assertThatThrownBy(
                () -> adminValidator.validateEqualsWithExistPassword(password, encodedPassword))
                .isInstanceOf(AdminEqualsPreviousPasswordExcpetion.class);
    }

    @Test
    @DisplayName("인증번호가 다를시 인증번호 오류 발생 예외가 발생한다.")
    public void validateAuthCodeFailTest() {
        //given
        Integer requestAuthCode = 123456;
        Integer storedAuthCode = 654321;

        //when
        assertThatThrownBy(() -> adminValidator.validateAuthCode(requestAuthCode, storedAuthCode))
                .isInstanceOf(AdminInvalidAuthCodeException.class);
    }
}
