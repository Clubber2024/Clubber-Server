package com.clubber.ClubberServer.domain.auth.implement;

import com.clubber.ClubberServer.domain.auth.vo.TokenVO;
import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import com.clubber.ClubberServer.global.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserTokenAppender {
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenUtil jwtTokenUtil;

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
