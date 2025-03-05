package com.clubber.ClubberServer.domain.admin.domain;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "adminPasswordFind")
public class AdminPasswordFind {

    @Id
    private String email;

    private Integer authCode;

    private Long ttl = 300L;

    @Builder
    public AdminPasswordFind(String email, Integer authCode, Long ttl) {
        this.email = email;
        this.authCode = authCode;
        this.ttl = ttl;
    }
}
