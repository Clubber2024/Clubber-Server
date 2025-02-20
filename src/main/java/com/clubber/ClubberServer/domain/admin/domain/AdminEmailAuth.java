package com.clubber.ClubberServer.domain.admin.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "adminEmailAuth")
public class AdminEmailAuth {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Indexed
	private final String email;

	private final String authRandomString;

	private final Long ttl = 300L;

	@Builder
	public AdminEmailAuth(Long id, String email, String authRandomString) {
		this.id = id;
		this.email = email;
		this.authRandomString = authRandomString;
	}
}
