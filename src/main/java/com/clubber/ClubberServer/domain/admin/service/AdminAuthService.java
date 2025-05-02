package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.admin.implement.AdminTokenAppender;
import com.clubber.ClubberServer.domain.admin.implement.AdminTokenReader;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import com.clubber.ClubberServer.domain.auth.vo.TokenVO;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final AdminReader adminReader;
    private final AdminValidator adminValidator;
    private final AdminTokenAppender adminTokenAppender;
    private final AdminTokenReader adminTokenReader;

    @Transactional
    public CreateAdminsLoginResponse createAdminsLogin(CreateAdminsLoginRequest loginRequest) {
        Admin admin = adminReader.getAdminByUsernameInLogin(loginRequest.getUsername());
        adminValidator.validatePasswordInLogin(loginRequest.getPassword(), admin.getPassword());
        TokenVO tokenVO = adminTokenAppender.createAdminsToken(admin);
        return CreateAdminsLoginResponse.of(admin, tokenVO.accessToken(), tokenVO.refreshToken());
    }

    @Transactional
    public CreateAdminsLoginResponse createAdminsReissueToken(String refreshToken) {
        Long adminId = adminTokenReader.parseRefreshTokenId(refreshToken);
        Admin admin = adminReader.getAdminById(adminId);
        TokenVO tokenVO = adminTokenAppender.createAdminsToken(admin);
        return CreateAdminsLoginResponse.of(admin, tokenVO.accessToken(), tokenVO.refreshToken());
    }

    @Transactional
    public void logout() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        adminTokenAppender.deleteRefreshTokenById(currentUserId);
    }
}
