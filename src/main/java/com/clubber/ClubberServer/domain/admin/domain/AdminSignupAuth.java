package com.clubber.ClubberServer.domain.admin.domain;

import com.clubber.ClubberServer.domain.admin.exception.AdminAlreadyEmailVerifiedException;
import org.springframework.data.annotation.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "adminEmailAuth")
public class AdminSignupAuth {

	@Id
	private String email;

	@Indexed
	private String authCode;

	@TimeToLive
	private Long ttl = 300L;

	private boolean isEmailVerified = false;

	@Builder
	public AdminSignupAuth(String email, String authCode) {
		this.email = email;
		this.authCode = authCode;
	}

	public void verify() {
		if (isEmailVerified) {
			throw AdminAlreadyEmailVerifiedException.EXCEPTION;
		}
		this.isEmailVerified = true;
	}
}
