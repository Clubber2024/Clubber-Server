package com.clubber.ClubberServer.unit.domain.admin.domain;

import static com.clubber.domain.domains.user.domain.AccountRole.ADMIN;
import static com.clubber.domain.domains.user.domain.AccountState.ACTIVE;
import static com.clubber.domain.domains.user.domain.AccountState.INACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.exception.AdminAlreadyDeletedException;
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
	public void adminWithdrawSuccessTest() {
		//given
		Admin admin = getAdmin();

		//when
		admin.withDraw();

		//then
		assertThat(admin.getAccountState()).isEqualTo(INACTIVE);
	}

	@Test
	@DisplayName("이미 비활성화된 계정에서 회원탈퇴시 예외가 발생한다.")
	public void adminWithdrawFailTest() {
		//given
		Admin inactiveAdmin = getInactiveAdmin();

		//when & then
		assertThatThrownBy(inactiveAdmin::withDraw)
			.isInstanceOf(AdminAlreadyDeletedException.class);
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

	private Admin getInactiveAdmin() {
		return Admin.builder()
			.id(1L)
			.accountRole(ADMIN)
			.accountState(INACTIVE)
			.password("password")
			.username("username")
			.build();
	}
}
