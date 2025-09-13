package com.clubber.domain.admin.service;

import com.clubber.domain.admin.domain.*;
import com.clubber.domain.admin.dto.CreateAdminSignupAuthVerifyRequest;
import com.clubber.domain.admin.dto.UpdateAdminPasswordFindAuthVerifyRequest;
import com.clubber.domain.admin.dto.UpdateAdminUpdateEmailAuthVerifyRequest;
import com.clubber.domain.domains.admin.exception.AdminInvalidAuthCodeException;
import com.clubber.domain.admin.implement.AdminReader;
import com.clubber.domain.admin.implement.AdminValidator;
import com.clubber.domain.admin.repository.AdminPasswordFindAuthRepository;
import com.clubber.domain.admin.repository.AdminSignupAuthRepository;
import com.clubber.domain.admin.repository.AdminUpdateEmailAuthRepository;
import com.clubber.domain.admin.repository.AdminUsernameFindAuthRepository;
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
    private final AdminUpdateEmailAuthRepository adminUpdateEmailAuthRepository;
    private final AdminReader adminReader;
    private final AdminUpdateEmailAuthRepository updateEmailAuthRepository;

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
    public void updateVerifyAdminSignupAuth(
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
    public void createAdminUpdateEmailAuth(Long adminId, String email, Integer authCode) {
        AdminUpdateEmailAuth adminUpdateEmailAuth = AdminUpdateEmailAuth.builder()
                .adminId(adminId)
                .email(email)
                .authCode(authCode)
                .build();
        adminUpdateEmailAuthRepository.save(adminUpdateEmailAuth);
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

    public void updateVerifyAdminEmailUpdateAuth(UpdateAdminUpdateEmailAuthVerifyRequest request) {
        Admin admin = adminReader.getCurrentAdmin();
        AdminUpdateEmailAuth adminUpdateEmailAuth = updateEmailAuthRepository.findById(admin.getId())
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

        adminValidator.validateAuthCode(request.getAuthCode(), adminUpdateEmailAuth.getAuthCode());
        adminUpdateEmailAuth.verify();;

        adminUpdateEmailAuthRepository.save(adminUpdateEmailAuth);
    }

    public void checkAdminSignupAuthVerified(String clubName, Integer authCode){
        AdminSignupAuth adminSignupAuth = adminSignupAuthRepository.findById(clubName)
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

        adminValidator.validateAuthCode(authCode, adminSignupAuth.getAuthCode());
        adminSignupAuth.checkIsVerified();
    }

    public void checkAdminUsernameFindAuthVerified(Long clubId, Integer authCode) {
        AdminUsernameFindAuth adminUsernameFindAuth = adminUsernameFindAuthRepository.findById(clubId)
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

        adminValidator.validateAuthCode(authCode, adminUsernameFindAuth.getAuthCode());
        adminUsernameFindAuth.checkIsVerified();
    }

    public void checkAdminPasswordFindAuthVerified(String username, Integer authCode) {
        AdminPasswordFindAuth adminPasswordFindAuth = adminPasswordFindAuthRepository.findById(username)
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

        adminValidator.validateAuthCode(authCode, adminPasswordFindAuth.getAuthCode());
        adminPasswordFindAuth.checkIsVerified();
    }

    public void checkAdminUpdateEmailAuthVerified(Long adminId, Integer authCode) {
        AdminUpdateEmailAuth adminUpdateEmailAuth = adminUpdateEmailAuthRepository.findById(adminId)
                .orElseThrow(() -> AdminInvalidAuthCodeException.EXCEPTION);

        adminValidator.validateAuthCode(authCode, adminUpdateEmailAuth.getAuthCode());
        adminUpdateEmailAuth.checkIsVerified();
    }

    public void deleteAdminSingupAuthById(String clubName) {
        adminSignupAuthRepository.deleteById(clubName);
    }

    public void deleteAdminPasswordFindAuthById(String username) {
        adminPasswordFindAuthRepository.deleteById(username);
    }

    public void deleteAdminUsernameFindAuthById(Long clubId){
        adminUsernameFindAuthRepository.deleteById(clubId);
    }

    public void deleteAdminUpdateEmailAuthById(Long adminId) {
        adminUpdateEmailAuthRepository.deleteById(adminId);
    }
}
