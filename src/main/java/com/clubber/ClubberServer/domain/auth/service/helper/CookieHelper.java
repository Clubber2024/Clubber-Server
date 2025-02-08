package com.clubber.ClubberServer.domain.auth.service.helper;

import com.clubber.ClubberServer.global.helper.SpringEnvironmentHelper;
import com.clubber.ClubberServer.global.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

/**
 * TODO 추후 FE에서 Cookie 적용 시 적용
 */
@RequiredArgsConstructor
@Component
public class CookieHelper {

	private final JwtProperties jwtProperties;

	private final SpringEnvironmentHelper springEnvironmentHelper;

	public HttpHeaders getCookies(String accessToken, String refreshToken) {
		String sameSite = "None";

		if (springEnvironmentHelper.isProdProfile()) {
			sameSite = "Strict";
		}

		ResponseCookie accessTokenCookie = ResponseCookie
			.from("accessToken", accessToken)
			.maxAge(jwtProperties.getAccessExp())
			.secure(true)
			.sameSite(sameSite)
			.httpOnly(true)
			.path("/")
			.build();

		ResponseCookie refreshTokenCookie = ResponseCookie
			.from("refreshToken", refreshToken)
			.maxAge(jwtProperties.getRefreshExp())
			.secure(true)
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

		if (springEnvironmentHelper.isProdProfile()) {
			sameSite = "Strict";
		}

		ResponseCookie accessTokenCookie = ResponseCookie
			.from("accessToken", null)
			.maxAge(0)
			.secure(true)
			.sameSite(sameSite)
			.httpOnly(true)
			.path("/")
			.build();

		ResponseCookie refreshTokenCookie = ResponseCookie
			.from("refreshToken", null)
			.maxAge(0)
			.secure(true)
			.sameSite(sameSite)
			.httpOnly(true)
			.path("/")
			.build();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
		httpHeaders.add(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
		return httpHeaders;
	}
}
