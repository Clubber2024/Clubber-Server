package com.clubber.ClubberServer.domain.admin.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "adminUsernameFindAuth")
public class AdminUsernameFindAuth {
    @Id
    private Long clubId;

    private Integer authCode;

    @TimeToLive
    private Long ttl = 300L;

    private boolean isVerified = false; 

    @Builder
    public AdminUsernameFindAuth(Long clubId, Integer authCode) {
        this.clubId = clubId;
        this.authCode = authCode;
    }
}
