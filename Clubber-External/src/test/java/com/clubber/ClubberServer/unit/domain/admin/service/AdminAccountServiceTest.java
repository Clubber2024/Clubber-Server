package com.clubber.ClubberServer.unit.domain.admin.service;

import com.clubber.domain.domains.admin.domain.Admin;
import com.clubber.domain.admin.dto.GetAdminsProfileResponse;
import com.clubber.domain.admin.dto.UpdateAdminsPasswordRequest;
import com.clubber.domain.admin.service.AdminAccountService;
import com.clubber.domain.admin.implement.AdminReader;
import com.clubber.domain.admin.implement.AdminValidator;
import com.clubber.global.event.withdraw.SoftDeleteEventPublisher;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.clubber.domain.domains.user.domain.AccountState.INACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminAccountServiceTest {

    @InjectMocks
    private AdminAccountService adminAccountService;

    @Mock
    private AdminReader adminReader;

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
        when(adminReader.getCurrentAdmin()).thenReturn(admin);

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
        when(adminReader.getCurrentAdmin()).thenReturn(admin);

        doNothing().when(adminValidator).validateEqualsWithExistPassword(anyString(), anyString());
        doNothing().when(adminValidator).validateExistPassword(anyString(), anyString());

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
    public void 관리자_회원탈퇴_계정_상태_변경() {
        //given
        Admin admin = AdminFixture.aAdmin().build();
        when(adminReader.getCurrentAdmin()).thenReturn(admin);

        doNothing().when(softDeleteEventPublisher).throwSoftDeleteEvent(anyLong());

        //when
        adminAccountService.withDraw();

        //then
        assertThat(admin.getAccountState()).isEqualTo(INACTIVE);
    }
}
