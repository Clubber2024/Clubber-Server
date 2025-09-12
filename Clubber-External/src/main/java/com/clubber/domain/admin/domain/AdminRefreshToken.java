package com.clubber.domain.admin.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash(value = "adminRefreshToken")
@Getter
public class AdminRefreshToken {

    @Id
    private Long id;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private Long ttl;

    @Builder
    private AdminRefreshToken(Long id, String refreshToken, Long ttl) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.ttl = ttl;
    }

    public static AdminRefreshToken of(Long id, String refreshToken, Long ttl){
        return AdminRefreshToken.builder()
                .id(id)
                .refreshToken(refreshToken)
                .ttl(ttl)
                .build();
    }
}
