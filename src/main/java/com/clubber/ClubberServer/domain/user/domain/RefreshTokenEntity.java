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
    private RefreshTokenEntity(Long id, String refreshToken, Long ttl) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.ttl = ttl;
    }

    public static RefreshTokenEntity of(Long id, String refreshToken, Long ttl){
        return RefreshTokenEntity.builder()
                .id(id)
                .refreshToken(refreshToken)
                .ttl(ttl)
                .build();
    }
}
