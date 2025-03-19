package com.clubber.ClubberServer.unit.domain.admin.facade;

import com.clubber.ClubberServer.domain.admin.dto.CreateAdminPasswordFindRequest;
import com.clubber.ClubberServer.domain.admin.facade.AdminEmailAuthFacade;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.global.infrastructure.outer.mail.MailService;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminEmailAuthFacadeTest {

    @InjectMocks
    private AdminEmailAuthFacade adminEmailAuthFacade;

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private MailService mailService;

    @Test
    @DisplayName("존재하지 않는 이메일로 비밀번호 찾기를 요청할 경우 메일이 전송되지 않는다.")
    void createAdminPasswordFindNotFoundEmail(){
        //given
        CreateAdminPasswordFindRequest request = AdminFixture.getDefaultCreateAdminPasswordFindRequest().sample();

        //when
        when(adminRepository.existsByEmailAndUsernameAndAccountState(anyString(), anyString(), eq(ACTIVE))).thenReturn(false);
        adminEmailAuthFacade.createAdminPasswordFind(request);

        //then
        verify(mailService, never()).send(anyString(), anyString(), anyString());
    }
}
