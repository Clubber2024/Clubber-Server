package com.clubber.domain.admin.facade;

import com.clubber.domain.admin.domain.Admin;
import com.clubber.domain.admin.domain.AdminSignupAuth;
import com.clubber.domain.admin.dto.*;
import com.clubber.domain.admin.implement.AdminReader;
import com.clubber.domain.admin.service.AdminEmailAuthService;
import com.clubber.global.infrastructure.outer.mail.MailService;
import com.clubber.global.util.RandomAuthCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminEmailAuthFacade {

    private final AdminEmailAuthService adminEmailAuthService;
    private final AdminReader adminReader;
    private final MailService mailService;

    public CreateAdminAuthResponse signupAdminAuth(
            CreateAdminSignupAuthRequest createAdminSignupAuthRequest) {
        String email = createAdminSignupAuthRequest.getEmail();
        String clubName = createAdminSignupAuthRequest.getClubName();

        Integer authCode = RandomAuthCodeUtil.getEmailAuthRandomNumber();
        mailService.sendAsync(email, "[클러버] 회원가입 인증 번호입니다.", authCode.toString());

        AdminSignupAuth adminMailAuth = adminEmailAuthService.createAdminSignupAuth(clubName, email, authCode);
        return CreateAdminAuthResponse.from(adminMailAuth);
    }

    public void usernameFindAdminAuth(CreateAdminUsernameFindAuthRequest createAdminUsernameFindAuthRequest) {
        Long clubId = createAdminUsernameFindAuthRequest.getClubId();
        String email = createAdminUsernameFindAuthRequest.getEmail();

        if (adminReader.existsByEmailAndClubId(email, clubId)) {
            Integer authCode = RandomAuthCodeUtil.getEmailAuthRandomNumber();
            mailService.sendAsync(email, "[클러버] 아이디 찾기 인증 번호입니다.", authCode.toString());

            adminEmailAuthService.createAdminUsernameFindAuth(clubId, authCode);
        }
    }

    public void createAdminPasswordFind(CreateAdminPasswordFindRequest createAdminPasswordFindRequest) {
        String username = createAdminPasswordFindRequest.getUsername();
        String email = createAdminPasswordFindRequest.getEmail();

        Admin admin = adminReader.getAdminByUsername(username);

        if (admin.getEmail().equals(email)) {
            Integer authCode = RandomAuthCodeUtil.getEmailAuthRandomNumber();
            mailService.sendAsync(email, "[클러버] 비밀번호 찾기 인증 번호입니다.", authCode.toString());

            adminEmailAuthService.createAdminPasswordFindAuth(username, authCode);
        }
    }

    public void createAdminEmailUpdateAuth(CreateAdminUpdateEmailAuthRequest request) {
        Integer authCode = RandomAuthCodeUtil.getEmailAuthRandomNumber();
        String email = request.getEmail();
        mailService.send(email, "[클러버] 이메일 변경 인증 번호입니다.", authCode.toString());

        Admin admin = adminReader.getCurrentAdmin();
        adminEmailAuthService.createAdminUpdateEmailAuth(admin.getId(), email, authCode);
    }
}
