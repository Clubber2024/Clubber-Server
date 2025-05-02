package com.clubber.ClubberServer.domain.admin.impl;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.auth.vo.TokenVO;
import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAppender {
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    public TokenVO createAdminsToken(Admin admin) {
        String accessToken = jwtTokenProvider.generateAccessToken(admin);
        String refreshToken = jwtTokenProvider.generateRefreshToken(admin.getId());
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.of(admin.getId(), refreshToken,
                jwtTokenProvider.getRefreshTokenTTlSecond());
        RefreshTokenEntity savedRefreshToken = refreshTokenRepository.save(refreshTokenEntity);
        return new TokenVO(accessToken, refreshToken);
    }
}
