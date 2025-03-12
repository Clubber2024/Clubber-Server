package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFindAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminSignupAuth;
import com.clubber.ClubberServer.domain.admin.domain.AdminUsernameFindAuth;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminPasswordFindAuthVerifyRequest;
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
    public AdminSignupAuth createAdminSignupAuth(String clubName, String email, Integer authCode) {
        AdminSignupAuth adminSignupAuth = AdminSignupAuth.builder()
                .clubName(clubName)
                .email(email)
                .authCode(authCode)
                .build();
        return adminSignupAuthRepository.save(adminSignupAuth);
    }

    @Transactional
    public void createAdminSignupAuthVerify(
            CreateAdminSignupAuthVerifyRequest createAdminVerifySignupAuthRequest) {
        String clubName = createAdminVerifySignupAuthRequest.getClubName();
        final Integer requestAuthCode = createAdminVerifySignupAuthRequest.getAuthCode();
        AdminSignupAuth adminSignupAuth = adminSignupAuthRepository.findById(clubName)
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

        adminValidator.validateAuthCode(requestAuthCode, adminSignupAuth.getAuthCode());
        adminSignupAuth.verify();

        adminSignupAuthRepository.save(adminSignupAuth);
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
    public void createAdminPasswordFindAuth(String username, Integer authCode) {
        AdminPasswordFindAuth adminPasswordFindAuth = AdminPasswordFindAuth.builder()
                .username(username)
                .authCode(authCode)
                .build();
        adminPasswordFindAuthRepository.save(adminPasswordFindAuth);
    }

    @Transactional
    public void updateAdminPasswordFindAuthVerify(UpdateAdminPasswordFindAuthVerifyRequest updateAdminPasswordFindAuthVerifyRequest) {
        String username = updateAdminPasswordFindAuthVerifyRequest.getUsername();
        Integer requestAuthCode = updateAdminPasswordFindAuthVerifyRequest.getAuthCode();

        AdminPasswordFindAuth adminPasswordFindAuth = adminPasswordFindAuthRepository.findById(username)
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

        adminValidator.validateAuthCode(requestAuthCode, adminPasswordFindAuth.getAuthCode());
        adminPasswordFindAuth.verify();
        adminPasswordFindAuthRepository.save(adminPasswordFindAuth);
    }

    public void updateVerifyAdminUsernameFindAuth(Long clubId, Integer authCode) {
        AdminUsernameFindAuth adminUsernameFindAuth = adminUsernameFindAuthRepository.findById(clubId)
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

        adminValidator.validateAuthCode(authCode, adminUsernameFindAuth.getAuthCode());
        adminUsernameFindAuth.verify();

        adminUsernameFindAuthRepository.save(adminUsernameFindAuth);
    }

    public void checkAdminUsernameFindAuthVerified(Long clubId, Integer authCode) {
        AdminUsernameFindAuth adminUsernameFindAuth = adminUsernameFindAuthRepository.findById(clubId)
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

        adminValidator.validateAuthCode(authCode, adminUsernameFindAuth.getAuthCode());
        adminUsernameFindAuth.checkIsVerified();
    }

    public void deleteAdminUsernameFindAuthById(Long clubId){
        adminUsernameFindAuthRepository.deleteById(clubId);
    }
}
