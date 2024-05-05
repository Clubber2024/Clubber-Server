package com.clubber.ClubberServer.domain.user.domain;


import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken")
@Getter
public class RefreshTokenEntity {

    @Id
    private Long id;

    private String refreshToken;

    private Long ttl;

    @Builder
    public RefreshTokenEntity(Long id, String refreshToken, Long ttl) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.ttl = ttl;
    }
}
