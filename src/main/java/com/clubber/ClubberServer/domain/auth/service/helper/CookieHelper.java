package com.clubber.ClubberServer.domain.auth.service.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.auth.dto.KakaoOauthResponse;
import com.clubber.ClubberServer.global.helper.SpringEnvironmentHelper;
import com.clubber.ClubberServer.global.jwt.JwtProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CookieHelper {
	private final JwtProperties jwtProperties;

	private final SpringEnvironmentHelper springEnvironmentHelper;

	public HttpHeaders getCookies(CreateAdminsLoginResponse createAdminsLoginResponse) {
		String sameSite = "None";

		if(springEnvironmentHelper.isProdProfile()){
			sameSite = "Strict";
		}

		ResponseCookie accessToken = ResponseCookie
			.from("accessToken", createAdminsLoginResponse.getAccessToken())
			.maxAge(jwtProperties.getAccessExp())
			.secure(false)
			.sameSite(sameSite)
			.httpOnly(true)
			.path("/")
			.build();

		ResponseCookie refreshToken = ResponseCookie
			.from("refreshToken", createAdminsLoginResponse.getRefreshToken())
			.maxAge(jwtProperties.getRefreshExp())
			.secure(false)
			.sameSite(sameSite)
			.httpOnly(true)
			.path("/")
			.build();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.SET_COOKIE, accessToken.toString());
		httpHeaders.add(HttpHeaders.SET_COOKIE, refreshToken.toString());
		return httpHeaders;
	}

	public HttpHeaders getCookies(KakaoOauthResponse kakaoOauthResponse) {
		String sameSite = "None";

		if(springEnvironmentHelper.isProdProfile()){
			sameSite = "Strict";
		}

		ResponseCookie accessToken = ResponseCookie
			.from("accessToken", kakaoOauthResponse.getAccessToken())
			.maxAge(jwtProperties.getAccessExp())
			.secure(true)
			.sameSite(sameSite)
			.httpOnly(true)
			.path("/")
			.build();

		ResponseCookie refreshToken = ResponseCookie
			.from("refreshToken", kakaoOauthResponse.getRefreshToken())
			.maxAge(jwtProperties.getRefreshExp())
			.secure(true)
			.sameSite(sameSite)
			.httpOnly(true)
			.path("/")
			.build();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.SET_COOKIE, accessToken.toString());
		httpHeaders.add(HttpHeaders.SET_COOKIE, refreshToken.toString());
		return httpHeaders;
	}

	public HttpHeaders deleteCookies() {

		String sameSite = "None";

		if(springEnvironmentHelper.isProdProfile()){
			sameSite = "Strict";
		}

		ResponseCookie accessToken = ResponseCookie
			.from("accessToken", null)
			.maxAge(0)
			.secure(true)
			.sameSite(sameSite)
			.httpOnly(true)
			.path("/")
			.build();

		ResponseCookie refreshToken = ResponseCookie
			.from("refreshToken", null)
			.maxAge(0)
			.secure(true)
			.sameSite(sameSite)
			.httpOnly(true)
			.path("/")
			.build();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.SET_COOKIE, accessToken.toString());
		httpHeaders.add(HttpHeaders.SET_COOKIE, refreshToken.toString());
		return httpHeaders;
	}
}
