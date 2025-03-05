package com.clubber.ClubberServer.domain.admin.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "adminSignupAuth")
public class AdminSignupAuth {

	@Id
	private String email;

	private Integer authCode;

	@TimeToLive
	private Long ttl = 300L;

	@Builder
	public AdminSignupAuth(String email, Integer authCode) {
		this.email = email;
		this.authCode = authCode;
	}
}
