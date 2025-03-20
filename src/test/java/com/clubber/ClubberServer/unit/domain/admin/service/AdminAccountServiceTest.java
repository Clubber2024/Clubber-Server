package com.clubber.ClubberServer.unit.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsProfileResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordRequest;
import com.clubber.ClubberServer.domain.admin.service.AdminAccountService;
import com.clubber.ClubberServer.domain.admin.service.AdminReadService;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.global.event.withdraw.SoftDeleteEventPublisher;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.clubber.ClubberServer.domain.user.domain.AccountRole.ADMIN;
import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;
import static com.clubber.ClubberServer.domain.user.domain.AccountState.INACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

    @Mock
    private SoftDeleteEventPublisher softDeleteEventPublisher;

    @Test
    public void 관리자_프로필_조회() {
        //given
        Admin admin = AdminFixture.aAdmin().build();
        when(adminReadService.getCurrentAdmin()).thenReturn(admin);

        //when
        GetAdminsProfileResponse response = adminAccountService.getAdminsProfile();

        //then
        assertAll(
                () -> assertThat(response.username()).isEqualTo(admin.getUsername()),
                () -> assertThat(response.contact()).isEqualTo(admin.getContact()),
                () -> assertThat(response.email()).isEqualTo(admin.getEmail())
        );
    }

    @Test
    public void 관리자_비밀번호_변경() {
        //given
        final String oldPassword = "oldPassword";
        final String newPassword = "newPassword";
        Admin admin = AdminFixture.aAdmin()
                .password(oldPassword)
                .build();
        when(adminReadService.getCurrentAdmin()).thenReturn(admin);

        doNothing().when(adminValidator).validateEqualsWithExistPassword(anyString(), anyString());
        doNothing().when(adminValidator).validatePasswordInUpdatePassword(anyString(), anyString());

        UpdateAdminsPasswordRequest request = AdminFixture.a_마이페이지_비밀번호_변경_요청()
                .set("newPassword", newPassword)
                .sample();
        when(passwordEncoder.encode(anyString())).thenReturn(newPassword);

        //when
        adminAccountService.updateAdminsPassword(request);

        //then
        assertThat(admin.getPassword()).isNotNull();
        assertThat(admin.getPassword()).isEqualTo(newPassword);
    }

    @Test
    @DisplayName("관리자 회원 탈퇴시 계정 상태가 변경된다.")
    public void adminWithDrawTest() {
        //given
        Admin admin = AdminFixture.aAdmin().build();
        when(adminReadService.getCurrentAdmin()).thenReturn(admin);

        doNothing().when(softDeleteEventPublisher).throwSoftDeleteEvent(anyLong());

        //when
        adminAccountService.withDraw();

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
