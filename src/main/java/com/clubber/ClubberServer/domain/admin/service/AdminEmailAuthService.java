package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminUsernameFindAuth;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminPasswordFindAuthVerifyRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminSignupAuthVerifyRequest;
import com.clubber.ClubberServer.domain.admin.exception.AdminInvalidAuthCodeException;
import com.clubber.ClubberServer.domain.admin.repository.AdminPasswordFindAuthRepository;
import com.clubber.ClubberServer.domain.admin.repository.AdminSignupAuthRepository;
import com.clubber.ClubberServer.domain.admin.repository.AdminUsernameFindAuthRepository;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminEmailAuthService {

    private final AdminSignupAuthRepository adminSignupAuthRepository;
    private final AdminPasswordFindAuthRepository adminPasswordFindAuthRepository;
    private final AdminUsernameFindAuthRepository adminUsernameFindAuthRepository;
    private final AdminValidator adminValidator;

    @Transactional
    public AdminSignupAuth createAdminSignupAuth(String email, Integer authCode) {
        AdminSignupAuth adminSignupAuth = AdminSignupAuth.builder()
                .email(email)
                .authCode(authCode)
                .build();
        return adminSignupAuthRepository.save(adminSignupAuth);
    }

    @Transactional
    public void createAdminSignupAuthVerify(
            CreateAdminSignupAuthVerifyRequest createAdminVerifySignupAuthRequest) {
        final String email = createAdminVerifySignupAuthRequest.getEmail();
        final Integer requestAuthCode = createAdminVerifySignupAuthRequest.getAuthCode();

        AdminSignupAuth adminSignupAuth = adminSignupAuthRepository.findById(email)
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);
        adminValidator.validateAuthCode(requestAuthCode, adminSignupAuth.getAuthCode());
        adminSignupAuthRepository.delete(adminSignupAuth);
    }

    @Transactional
    public void createAdminUsernameFindAuth(Long clubId, Integer authCode) {
        AdminUsernameFindAuth adminUsernameFindAuth = AdminUsernameFindAuth.builder()
                .clubId(clubId)
                .authCode(authCode)
                .build();
        adminUsernameFindAuthRepository.save(adminUsernameFindAuth);
    }

    @Transactional
    public void createAdminPasswordFindAuth(String email, Integer authCode) {
        AdminPasswordFindAuth adminPasswordFindAuth = AdminPasswordFindAuth.builder()
                .email(email)
                .authCode(authCode)
                .build();
        adminPasswordFindAuthRepository.save(adminPasswordFindAuth);
    }

    @Transactional
    public void createAdminPasswordFindAuthVerify(CreateAdminPasswordFindAuthVerifyRequest createAdminPasswordFindAuthVerifyRequest) {
        String email = createAdminPasswordFindAuthVerifyRequest.getEmail();
        Integer requestAuthCode = createAdminPasswordFindAuthVerifyRequest.getAuthCode();
        AdminPasswordFindAuth adminPasswordFindAuth = adminPasswordFindAuthRepository.findById(email)
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

        adminValidator.validateAuthCode(requestAuthCode, adminPasswordFindAuth.getAuthCode());
        adminPasswordFindAuthRepository.delete(adminPasswordFindAuth);
    }

    public void validateAdminUsernameFindAuth(Long clubId, Integer authCode) {
        AdminUsernameFindAuth adminUsernameFindAuth = adminUsernameFindAuthRepository.findById(clubId)
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);
        adminValidator.validateAuthCode(authCode, adminUsernameFindAuth.getAuthCode());
        adminUsernameFindAuth.checkIsVerified();
        adminUsernameFindAuthRepository.delete(adminUsernameFindAuth);
    }
}
