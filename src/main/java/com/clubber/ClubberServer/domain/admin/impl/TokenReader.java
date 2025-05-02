package com.clubber.ClubberServer.domain.admin.impl;

import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import com.clubber.ClubberServer.domain.user.exception.RefreshTokenExpiredException;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenReader {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenEntity getRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);
    }
}
