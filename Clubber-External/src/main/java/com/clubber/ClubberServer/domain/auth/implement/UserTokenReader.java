package com.clubber.ClubberServer.domain.auth.implement;

import com.clubber.ClubberServer.domain.auth.domain.UserRefreshToken;
import com.clubber.ClubberServer.domain.auth.repository.UserRefreshTokenRepository;
import com.clubber.domain.domains.user.exception.RefreshTokenExpiredException;
import com.clubber.ClubberServer.global.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserTokenReader {
    private final UserRefreshTokenRepository userRefreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public Long parseRefreshTokenId(String refreshToken) {
        UserRefreshToken userRefreshToken = userRefreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);
        return jwtTokenUtil.parseRefreshToken(userRefreshToken.getRefreshToken());
    }
}
