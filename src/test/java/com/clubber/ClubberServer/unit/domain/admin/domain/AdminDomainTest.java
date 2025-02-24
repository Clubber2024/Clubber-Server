package com.clubber.ClubberServer.unit.domain.admin.domain;

import static com.clubber.ClubberServer.domain.user.domain.AccountRole.ADMIN;
import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;
import static com.clubber.ClubberServer.domain.user.domain.AccountState.INACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AdminDomainTest {

	@Test
	@DisplayName("비밀번호 변경을 수행한다.")
	public void adminUpdatePasswordTest() {
		//given
		Admin admin = getAdmin();
		String newPassword = "new Password";

		//when
		admin.updatePassword(newPassword);

		//then
		assertThat(admin.getPassword()).isEqualTo(newPassword);
	}

	@Test
	@DisplayName("회원탈퇴를 수행하면 계정상태가 비활성화된다.")
	public void adminWithdrawTest() {
		//given
		Admin admin = getAdmin();

		//when
		admin.withDraw();

		//then
		assertThat(admin.getAccountState()).isEqualTo(INACTIVE);
	}

	private Admin getAdmin() {
		return Admin.builder()
			.id(1L)
			.accountRole(ADMIN)
			.accountState(ACTIVE)
			.password("password")
			.username("username")
			.build();
	}
}
