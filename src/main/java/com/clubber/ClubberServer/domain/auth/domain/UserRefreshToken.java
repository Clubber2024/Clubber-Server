package com.clubber.ClubberServer.domain.auth.domain;

import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "userRefreshToken")
@Getter
public class UserRefreshToken {
    @Id
    private Long id;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private Long ttl;

    @Builder
    private UserRefreshToken(Long id, String refreshToken, Long ttl) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.ttl = ttl;
    }

    public static UserRefreshToken of(Long id, String refreshToken, Long ttl){
        return UserRefreshToken.builder()
                .id(id)
                .refreshToken(refreshToken)
                .ttl(ttl)
                .build();
    }
}
