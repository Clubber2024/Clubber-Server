package com.clubber.ClubberServer.domain.admin.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "adminPasswordFind")
public class AdminPasswordFindAuth {

    @Id
    private String email;

    private Integer authCode;

    private Long ttl = 300L;

    @Builder
    public AdminPasswordFindAuth(String email, Integer authCode) {
        this.email = email;
        this.authCode = authCode;
    }
}
