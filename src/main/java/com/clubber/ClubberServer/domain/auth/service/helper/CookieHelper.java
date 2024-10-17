package com.clubber.ClubberServer.domain.auth.service.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.clubber.ClubberServer.global.helper.SpringEnvironmentHelper;
import com.clubber.ClubberServer.global.jwt.JwtProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CookieHelper {
	private final JwtProperties jwtProperties;

	private final SpringEnvironmentHelper springEnvironmentHelper;

	public HttpHeaders getCookies(String accessToken, String refreshToken) {
		String sameSite = "None";

		if(springEnvironmentHelper.isProdProfile()){
			sameSite = "Strict";
		}

		ResponseCookie accessTokenCookie = ResponseCookie
			.from("accessToken", accessToken)
			.maxAge(jwtProperties.getAccessExp())
			.secure(false)
			.sameSite(sameSite)
			.httpOnly(true)
			.path("/")
			.build();

		ResponseCookie refreshTokenCookie = ResponseCookie
			.from("refreshToken", refreshToken)
			.maxAge(jwtProperties.getRefreshExp())
			.secure(false)
			.sameSite(sameSite)
			.httpOnly(true)
			.path("/")
			.build();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
		httpHeaders.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
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
