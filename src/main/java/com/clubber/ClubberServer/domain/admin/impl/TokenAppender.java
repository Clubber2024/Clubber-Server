package com.clubber.ClubberServer.domain.admin.impl;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.auth.vo.TokenVO;
import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import com.clubber.ClubberServer.global.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenAppender {
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenUtil jwtTokenUtil;

    public TokenVO createAdminsToken(Admin admin) {
        String accessToken = jwtTokenUtil.generateAccessToken(admin.getId(), admin.getAccountRole());
        String refreshToken = jwtTokenUtil.generateRefreshToken(admin.getId());

        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.of(admin.getId(), refreshToken,
                jwtTokenUtil.getRefreshTokenTTlSecond());
        refreshTokenRepository.save(refreshTokenEntity);
        return new TokenVO(accessToken, refreshToken);
    }

    public TokenVO generateUserToken(User user) {
        String accessToken = jwtTokenUtil.generateAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getId());

        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.of(user.getId(), refreshToken,
                jwtTokenUtil.getRefreshTokenTTlSecond());
        refreshTokenRepository.save(refreshTokenEntity);
        return new TokenVO(accessToken, refreshToken);
    }

    public void deleteRefreshTokenById(Long id) {
        refreshTokenRepository.deleteById(id);
    }
}
