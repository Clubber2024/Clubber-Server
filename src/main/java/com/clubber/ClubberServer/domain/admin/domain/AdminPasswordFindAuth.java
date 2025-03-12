package com.clubber.ClubberServer.domain.admin.domain;

import com.clubber.ClubberServer.domain.admin.exception.AdminAlreadyEmailVerifiedException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "adminPasswordFind")
public class AdminPasswordFindAuth {

    @Id
    private String username;

    private Integer authCode;

    @TimeToLive
    private Long ttl = 300L;

    private boolean isVerified = false;

    @Builder
    public AdminPasswordFindAuth(String username, Integer authCode) {
        this.username = username;
        this.authCode = authCode;
    }

    public void verify() {
        if (isVerified) {
            throw AdminAlreadyEmailVerifiedException.EXCEPTION;
        }
        this.isVerified = true;
    }
}
