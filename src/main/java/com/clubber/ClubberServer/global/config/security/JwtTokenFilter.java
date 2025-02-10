package com.clubber.ClubberServer.global.config.security;

import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.AUTH_HEADER;
import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.BEARER;

import com.clubber.ClubberServer.global.dto.AccessTokenInfo;
import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String token = resolveToken(request);
		if (token != null) {
			Authentication authentication = getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {

		Cookie accessTokenCookie = WebUtils.getCookie(request, "accessToken");
		if (accessTokenCookie != null) {
			return accessTokenCookie.getValue();
		}

		String requestHeader = request.getHeader(AUTH_HEADER);

		if (requestHeader != null
			&& requestHeader.length() > BEARER.length()
			&& requestHeader.startsWith(BEARER)) {
			return requestHeader.substring(BEARER.length());
		}
		return null;
	}

	private Authentication getAuthentication(String token) {
		AccessTokenInfo accessTokenInfo = jwtTokenProvider.parseAccessToken(token);

		UserDetails userDetails = new AuthDetails(accessTokenInfo.getUserId().toString(),
			accessTokenInfo.getRole());

		return new UsernamePasswordAuthenticationToken(
			userDetails, "user", userDetails.getAuthorities()
		);
	}
}
