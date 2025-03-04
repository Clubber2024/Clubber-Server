package com.clubber.ClubberServer.domain.admin.controller;

import com.clubber.ClubberServer.domain.admin.dto.CreateAdminAuthResponse;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminMailAuthRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminPasswordFindAuthVerifyRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminSignupAuthVerifyRequest;
import com.clubber.ClubberServer.domain.admin.facade.AdminEmailAuthFacade;
import com.clubber.ClubberServer.domain.admin.service.AdminEmailAuthService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins/auths")
@Tag(name = "[동아리 계정 이메일 인증 API]", description = "동아리 계정 이메일 인증 및 연관 API")
public class AdminEmailAuthController {

    private final AdminEmailAuthFacade adminEmailAuthFacade;
    private final AdminEmailAuthService adminEmailAuthService;

    @DisableSwaggerSecurity
    @Operation(summary = "동아리 계정 회원가입시 인증번호 메일 전송")
    @PostMapping("/sign-up/send")
    public CreateAdminAuthResponse createAdminSignupAuth(
            @Valid @RequestBody CreateAdminMailAuthRequest createAdminMailAuthRequest) {
        return adminEmailAuthFacade.signupAdminAuth(createAdminMailAuthRequest);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "동아리 계정 회원가입시 인증 번호 검증")
    @PostMapping("/sign-up/verify")
    public void updateAdminInfo(
            @Valid @RequestBody CreateAdminSignupAuthVerifyRequest createAdminVerifySignupAuthRequest) {
        adminEmailAuthService.createAdminSignupAuthVerify(createAdminVerifySignupAuthRequest);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "동아리 비밀번호 찾기시 인증번호 메일 전송")
    @PostMapping("/find-password/send")
    public void createAdminPasswordFindVerify(
            @Valid @RequestBody CreateAdminMailAuthRequest createAdminMailAuthRequest
    ) {
        adminEmailAuthFacade.passwordFindAdminAuth(createAdminMailAuthRequest);
    }


    @DisableSwaggerSecurity
    @Operation(summary = "동아리 비밀번호 찾기시 인증번호 검증")
    @PostMapping("/find-password/verify")
    public void createAdminPasswordFindAuthVerify(
            @Valid @RequestBody CreateAdminPasswordFindAuthVerifyRequest createAdminPasswordFindAuthVerifyRequest) {
        adminEmailAuthService.createAdminPasswordFindAuthVerify(createAdminPasswordFindAuthVerifyRequest);
    }
}
