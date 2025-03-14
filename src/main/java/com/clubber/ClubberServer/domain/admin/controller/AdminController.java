package com.clubber.ClubberServer.domain.admin.controller;

import com.clubber.ClubberServer.domain.admin.dto.*;
import com.clubber.ClubberServer.domain.admin.service.AdminAccountService;
import com.clubber.ClubberServer.domain.admin.service.AdminAuthService;
import com.clubber.ClubberServer.domain.admin.service.AdminClubService;
import com.clubber.ClubberServer.domain.auth.service.helper.CookieHelper;
import com.clubber.ClubberServer.domain.club.dto.GetClubResponse;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admins")
@Tag(name = "[동아리 계정 API]", description = "🔐동아리 계정")
public class AdminController {

    private final AdminAuthService adminAuthService;

    private final AdminAccountService adminAccountService;

    private final AdminClubService adminClubService;

    private final CookieHelper cookieHelper;

    @DisableSwaggerSecurity
    @Operation(summary = "동아리 계정 로그인")
    @PostMapping("/login")
    public ResponseEntity<CreateAdminsLoginResponse> createAdminsLogin(
            @RequestBody @Valid CreateAdminsLoginRequest loginRequest) {
        CreateAdminsLoginResponse createAdminsLoginResponse = adminAuthService.createAdminsLogin(
                loginRequest);
        return ResponseEntity.ok()
//			.headers(cookieHelper.getCookies(createAdminsLoginResponse.getAccessToken(), createAdminsLoginResponse.getRefreshToken()))
                .body(createAdminsLoginResponse);
    }

    @Operation(summary = "동아리 계정 로그아웃")
    @PostMapping("/logout")
    public ResponseEntity createAdminsLogout() {
        adminAuthService.logout();
        return ResponseEntity.ok()
//			.headers(cookieHelper.deleteCookies())
                .body(null);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "동아리 계정 토큰 재발급")
    @PostMapping("/refresh")
    public ResponseEntity<CreateAdminsLoginResponse> createAdminsTokenRefresh(
//		@CookieValue(value = "refreshToken", required = false) String refreshTokenCookie,
            @RequestHeader(value = "refreshToken", required = false) String refreshToken) {
        CreateAdminsLoginResponse createAdminsLoginResponse = adminAuthService.getAdminsParseToken(
                refreshToken);
        return ResponseEntity.ok()
//			.headers(cookieHelper.getCookies(createAdminsLoginResponse.getAccessToken(),
//				createAdminsLoginResponse.getRefreshToken()))
                .body(createAdminsLoginResponse);
    }

    @Operation(summary = "메인페이지 동아리 계정 정보")
    @GetMapping("/me")
    public GetAdminsProfileResponse getAdminsProfile() {
        return adminAccountService.getAdminsProfile();
    }

    @Operation(summary = "동아리 계정 비밀번호 수정")
    @PatchMapping("/me/password")
    public UpdateAdminsPasswordResponse updateAdminsPassword(@RequestBody @Valid
                                                             UpdateAdminsPasswordRequest updateAdminsPasswordRequest) {
        return adminAccountService.updateAdminsPassword(updateAdminsPasswordRequest);
    }

    @Operation(summary = "동아리 계정 회원탈퇴")
    @DeleteMapping("/withdraw")
    public ResponseEntity withdrawAdmin() {
        adminAccountService.withDraw();
        return ResponseEntity.ok()
//			.headers(cookieHelper.deleteCookies())
                .body(null);
    }

    @Operation(summary = "동아리 계정 동아리 정보 페이지 조회")
    @GetMapping("/mypage")
    public GetClubResponse getAdminsMyPage() {
        return adminClubService.getAdminsMyPage();
    }

    @Operation(summary = "관리자 계정 동아리 정보 페이지 수정")
    @PatchMapping("/change-page")
    public UpdateClubPageResponse updateAdminsPage(
            @RequestBody @Valid UpdateClubPageRequest updateClubPageRequest) {
        return adminClubService.updateAdminsPage(updateClubPageRequest);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "동아리 회원가입 폼 작성")
    @PostMapping("/sign-up")
    public CreateAdminSignUpResponse createAdminSignUp(
            @Valid @RequestBody CreateAdminSignUpRequest createAdminSignUpRequest) {
        return adminAccountService.createAdminSignUp(createAdminSignUpRequest);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "동아리 회원가입 시 로그인 중복 확인")
    @GetMapping("/username/duplicate")
    public GetAdminUsernameCheckDuplicateResponse getAdminUsernameCheckDuplicate(@RequestParam String username){
        return adminAccountService.getAdminUsernameCheckDuplicate(username);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "아이디 찾기")
    @PostMapping("/username/find")
    public GetAdminUsernameFindResponse getAdminUsernameFind(@RequestBody GetAdminUsernameFindRequest request){
        return adminAccountService.getAdminUsernameFind(request);
    }

    @DisableSwaggerSecurity
    @Operation(summary = "비밀번호 찾기 인증 후 비밀번호 변경")
    @PatchMapping("/reset-password")
    public void getAdminResetPassword(@RequestBody UpdateAdminResetPasswordRequest request){
        adminAccountService.updateAdminResetPassword(request);
    }
}
