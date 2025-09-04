package com.clubber.ClubberServer.domain.admin.implement;

import com.clubber.ClubberServer.domain.admin.domain.AdminRefreshToken;
import com.clubber.ClubberServer.domain.admin.repository.AdminRefreshTokenRepository;
import com.clubber.ClubberServer.domain.user.exception.RefreshTokenExpiredException;
import com.clubber.ClubberServer.global.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminTokenReader {
    private final AdminRefreshTokenRepository adminRefreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public AdminRefreshToken getRefreshToken(String refreshToken) {
        return adminRefreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);
    }

    public Long parseRefreshTokenId(String refreshToken) {
        AdminRefreshToken adminRefreshToken = adminRefreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);
        return jwtTokenUtil.parseRefreshToken(adminRefreshToken.getRefreshToken());
    }
}
