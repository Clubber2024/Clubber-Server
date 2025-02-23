package com.clubber.ClubberServer.unit.domain.admin;

import static com.clubber.ClubberServer.domain.user.domain.AccountRole.ADMIN;
import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsProfileResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordRequest;
import com.clubber.ClubberServer.domain.admin.service.AdminAccountService;
import com.clubber.ClubberServer.domain.admin.service.AdminReadService;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AdminAccountServiceTest {

	@InjectMocks
	private AdminAccountService adminAccountService;

	@Mock
	private AdminReadService adminReadService;

	@Mock
	private AdminValidator adminValidator;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("관리자 프로필 조회를 수행한다.")
	public void getAdminsAccountProfile() {
		//given
		Admin admin = getAdmin();
		when(adminReadService.getCurrentAdmin()).thenReturn(admin);

		//when
		GetAdminsProfileResponse response = adminAccountService.getAdminsProfile();

		//then
		assertThat(response).isNotNull();
		assertThat(response.getClubName()).isEqualTo("club1");
	}

	@Test
	@DisplayName("관리자 비밀번호 정보를 수행한다.")
	public void updateAdminsPasswordTest() {
		//given
		Admin admin = getAdmin();
		UpdateAdminsPasswordRequest updatePasswordRequest = AdminFixture.VALID_UPDATE_PASSWORD_REQUEST;
		when(adminReadService.getCurrentAdmin()).thenReturn(admin);
		doNothing().when(adminValidator).validateEqualsWithExistPassword(anyString(), anyString());
		when(passwordEncoder.encode(updatePasswordRequest.getPassword())).thenReturn(
			"newPassword");

		//when
		adminAccountService.updateAdminsPassword(updatePasswordRequest);

		//then
		assertThat(admin.getPassword()).isNotNull(); 
		assertThat(admin.getPassword()).isEqualTo("newPassword");
	}

	private Admin getAdmin() {
		return Admin.builder()
			.id(1L)
			.accountRole(ADMIN)
			.accountState(ACTIVE)
			.password("password")
			.username("username")
			.club(getClub())
			.build();
	}

	private static Club getClub() {
		return Club.builder()
			.id(1L)
			.name("club1")
			.isAgreeToReview(true)
			.build();
	}
}
