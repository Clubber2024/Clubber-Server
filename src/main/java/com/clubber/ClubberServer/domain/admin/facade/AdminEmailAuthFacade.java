package com.clubber.ClubberServer.domain.admin.facade;

import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminPasswordFindRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminUsernameFindAuthRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminSignupAuthRequest;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminEmailAuthService;
import com.clubber.ClubberServer.global.infrastructure.outer.mail.MailService;
import com.clubber.ClubberServer.global.util.RandomAuthCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;

@Component
@RequiredArgsConstructor
public class AdminEmailAuthFacade {

    private final AdminEmailAuthService adminEmailAuthService;
    private final AdminRepository adminRepository;
    private final MailService mailService;

    public CreateAdminAuthResponse signupAdminAuth(
            CreateAdminSignupAuthRequest createAdminSignupAuthRequest) {
        final String email = createAdminSignupAuthRequest.getEmail();
        Integer authCode = RandomAuthCodeUtil.generateRandomInteger(6);

        mailService.send(email, "[클러버] 회원가입 인증 번호입니다.", authCode.toString());
        AdminSignupAuth adminMailAuth = adminEmailAuthService.createAdminSignupAuth(email, authCode);
        return CreateAdminAuthResponse.from(adminMailAuth);
    }

    public void usernameFindAdminAuth(CreateAdminUsernameFindAuthRequest createAdminUsernameFindAuthRequest) {
        Long clubId = createAdminUsernameFindAuthRequest.getClubId();
        String email = createAdminUsernameFindAuthRequest.getEmail();

        if (adminRepository.existsByEmailAndClubIdAndAccountState(email, clubId, ACTIVE)) {
            Integer authCode = RandomAuthCodeUtil.generateRandomInteger(6);
            mailService.send(email, "[클러버] 아이디 찾기 인증 번호입니다.", authCode.toString());

            adminEmailAuthService.createAdminUsernameFindAuth(clubId, authCode);
        }
    }

    public void createAdminPasswordFind(CreateAdminPasswordFindRequest createAdminPasswordFindRequest) {
        String username = createAdminPasswordFindRequest.getUsername();
        String email = createAdminPasswordFindRequest.getEmail();

        if (adminRepository.existsByEmailAndUsernameAndAccountState(email, username, ACTIVE)) {
            Integer authCode = RandomAuthCodeUtil.generateRandomInteger(6);
            mailService.send(email, "[클러버] 비밀번호 찾기 인증 번호입니다.", authCode.toString());

            adminEmailAuthService.createAdminPasswordFindAuth(username, authCode);
        }
    }
}
