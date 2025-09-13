package com.clubber.domain.admin.domain;

import com.clubber.domain.domains.admin.exception.AdminAlreadyEmailVerifiedException;
import com.clubber.domain.domains.admin.exception.AdminInvalidAuthCodeException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "adminUpdateEmailAuth")
public class AdminUpdateEmailAuth {
    @Id
    private Long adminId;

    private String email;

    private Integer authCode;

    private boolean isVerified = false;

    @Builder
    public AdminUpdateEmailAuth(Long adminId, String email, Integer authCode) {
        this.adminId = adminId;
        this.email = email;
        this.authCode = authCode;
    }

    public void verify() {
        if (isVerified) {
            throw AdminAlreadyEmailVerifiedException.EXCEPTION;
        }
        this.isVerified = true;
    }

    public void checkIsVerified() {
        if (!isVerified) {
            throw AdminInvalidAuthCodeException.EXCEPTION;
        }
    }
}
