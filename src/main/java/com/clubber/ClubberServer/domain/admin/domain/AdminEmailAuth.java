package com.clubber.ClubberServer.domain.admin.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "adminEmailAuth")
public class AdminEmailAuth {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Indexed
	private String email;

	@Indexed
	private String authCode;

	@TimeToLive
	private Long ttl = 300L;

	@Builder
	public AdminEmailAuth(Long id, String email, String authCode) {
		this.id = id;
		this.email = email;
		this.authCode = authCode;
	}
}
