package com.clubber.ClubberServer.global.jwt;


import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.ACCESS_TOKEN;
import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.MILLI_TO_SECOND;
import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.REFRESH_TOKEN;
import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.TOKEN_ISSUER;
import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.TOKEN_ROLE;
import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.TOKEN_TYPE;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.owner.domain.Owner;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.exception.InvalidTokenException;
import com.clubber.ClubberServer.domain.user.exception.RefreshTokenExpiredException;
import com.clubber.ClubberServer.domain.user.exception.TokenExpiredException;
import com.clubber.ClubberServer.global.dto.AccessTokenInfo;
import com.clubber.ClubberServer.global.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;

	private Jws<Claims> getJws(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw TokenExpiredException.EXCEPTION;
		} catch (Exception e) {
			throw InvalidTokenException.EXCEPTION;
		}

	}

	public String generateAccessToken(User user) {
		final Key encodedKey = getSecretKey();
		final Date issuedAt = new Date();
		final Date accessTokenExpiresIn =
			new Date(issuedAt.getTime() + jwtProperties.getAccessExp() * MILLI_TO_SECOND);

		return Jwts.builder()
			.setIssuer(TOKEN_ISSUER)
			.setIssuedAt(issuedAt)
			.setSubject(user.getId().toString())
			.claim(TOKEN_TYPE, ACCESS_TOKEN)
			.claim(TOKEN_ROLE, user.getRole().name())
			.setExpiration(accessTokenExpiresIn)
			.signWith(encodedKey)
			.compact();
	}

	private Key getSecretKey() {
		return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
	}

	public String generateAccessToken(Admin admin) {
		final Key encodedKey = getSecretKey();
		final Date issuedAt = new Date();
		final Date accessTokenExpiresIn =
			new Date(issuedAt.getTime() + jwtProperties.getAccessExp() * MILLI_TO_SECOND);

		return Jwts.builder()
			.setIssuer(TOKEN_ISSUER)
			.setIssuedAt(issuedAt)
			.setSubject(admin.getId().toString())
			.claim(TOKEN_TYPE, ACCESS_TOKEN)
			.claim(TOKEN_ROLE, admin.getAccountRole().name())
			.setExpiration(accessTokenExpiresIn)
			.signWith(encodedKey)
			.compact();
	}

	public String generateAccessToken(Owner owner) {
		final Key encodedKey = getSecretKey();
		final Date issuedAt = new Date();
		final Date accessTokenExpiresIn =
			new Date(issuedAt.getTime() + jwtProperties.getAccessExp() * MILLI_TO_SECOND);

		return Jwts.builder()
			.setIssuer(TOKEN_ISSUER)
			.setIssuedAt(issuedAt)
			.setSubject(owner.getId().toString())
			.claim(TOKEN_TYPE, ACCESS_TOKEN)
			.claim(TOKEN_ROLE, owner.getAccountRole().name())
			.setExpiration(accessTokenExpiresIn)
			.signWith(encodedKey)
			.compact();
	}

	public String generateRefreshToken(Long id) {
		final Key encodedKey = getSecretKey();
		final Date issuedAt = new Date();
		final Date refreshTokenExpiresIn =
			new Date(issuedAt.getTime() + jwtProperties.getRefreshExp() * MILLI_TO_SECOND);

		return Jwts.builder()
			.setIssuer(TOKEN_ISSUER)
			.setIssuedAt(issuedAt)
			.setSubject(id.toString())
			.claim(TOKEN_TYPE, REFRESH_TOKEN)
			.setExpiration(refreshTokenExpiresIn)
			.signWith(encodedKey)
			.compact();
	}

	private boolean isAccessToken(String token) {
		Jws<Claims> jws = getJws(token);
		return jws.getBody().get(TOKEN_TYPE).equals(ACCESS_TOKEN);
	}

	public AccessTokenInfo parseAccessToken(String token) {
		if (isAccessToken(token)) {
			Claims claims = getJws(token).getBody();
			return AccessTokenInfo.builder()
				.userId(Long.parseLong(claims.getSubject()))
				.role((String) claims.get(TOKEN_ROLE))
				.build();
		}
		throw InvalidTokenException.EXCEPTION;
	}

	private boolean isRefreshToken(String token) {
		Jws<Claims> jws = getJws(token);
		return jws.getBody().get(TOKEN_TYPE).equals(REFRESH_TOKEN);
	}

	public Long parseRefreshToken(String token) {
		try {
			if (isRefreshToken(token)) {
				Claims claims = getJws(token).getBody();
				return Long.parseLong(claims.getSubject());
			}
		} catch (TokenExpiredException e) {
			throw RefreshTokenExpiredException.EXCEPTION;
		}
		throw InvalidTokenException.EXCEPTION;
	}

	public Long getRefreshTokenTTlSecond() {
		return jwtProperties.getRefreshExp();
	}
}
