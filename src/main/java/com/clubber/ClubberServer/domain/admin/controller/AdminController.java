package com.clubber.ClubberServer.domain.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsProfileResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageResponse;
import com.clubber.ClubberServer.domain.admin.service.AdminService;
import com.clubber.ClubberServer.domain.auth.service.helper.CookieHelper;
import com.clubber.ClubberServer.domain.club.dto.GetClubResponse;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins")
@Tag(name = "[동아리 계정 API]")
public class AdminController {

	private final AdminService adminService;

	private final CookieHelper cookieHelper;

	@DisableSwaggerSecurity
	@Operation(summary = "동아리 계정 로그인")
	@PostMapping("/login")
	public ResponseEntity<CreateAdminsLoginResponse> createAdminsLogin(
		@RequestBody @Valid CreateAdminsLoginRequest loginRequest) {
		CreateAdminsLoginResponse createAdminsLoginResponse = adminService.createAdminsLogin(loginRequest);
		return ResponseEntity.ok()
			.headers(cookieHelper.getCookies(createAdminsLoginResponse))
			.body(createAdminsLoginResponse);
	}

	@Operation(summary = "메인페이지 동아리 정보")
	@GetMapping("/me")
	public GetAdminsProfileResponse getAdminsProfile() {
		return adminService.getAdminsProfile();
	}

	@Operation(summary = "동아리 계정 비밀번호 수정")
	@PatchMapping("/me")
	public UpdateAdminsPasswordResponse updateAdminsPassword(@RequestBody @Valid
	UpdateAdminsPasswordRequest updateAdminsPasswordRequest) {
		return adminService.updateAdminsPassword(updateAdminsPasswordRequest);
	}

	@Operation(summary = "동아리 계정 로그아웃")
	@PostMapping("/logout")
	public ResponseEntity createAdminsLogout() {
		adminService.logout();
		return ResponseEntity.ok()
			.headers(cookieHelper.deleteCookies())
			.body(null);
	}

	@DisableSwaggerSecurity
	@Operation(summary = "동아리 계정 토큰 재발급")
	@PostMapping("/refresh")
	public ResponseEntity<CreateAdminsLoginResponse> createAdminsTokenRefresh(
		@CookieValue(value = "refreshToken", required = false) String refreshTokenCookie,
		@RequestHeader(value = "refreshToken", required = false) String refreshToken) {
		CreateAdminsLoginResponse createAdminsLoginResponse = adminService.getAdminsParseToken(
			refreshTokenCookie != null ? refreshTokenCookie : refreshToken);
		return ResponseEntity.ok()
			.headers(cookieHelper.getCookies(createAdminsLoginResponse))
			.body(createAdminsLoginResponse);
	}

	@Operation(summary = "동아리 계정 회원탈퇴")
	@DeleteMapping("/withdraw")
	public ResponseEntity withdrawAdmin() {
		adminService.withDraw();
		return ResponseEntity.ok()
			.headers(cookieHelper.deleteCookies())
			.body(null);
	}

	@Operation(summary = "개별 동아리 계정 페이지 수정")
	@PatchMapping("/change-page")
	public UpdateClubPageResponse updateAdminsPage(@RequestBody @Valid UpdateClubPageRequest pageRequest) {
		return adminService.updateAdminsPage(pageRequest);
	}

	@Operation(summary = "동아리 계정 마이 페이지 첫화면")
	@GetMapping("/mypage")
	public GetClubResponse getAdminsMyPage() {
		return adminService.getAdminsMyPage();
	}

}
