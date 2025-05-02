package com.clubber.ClubberServer.domain.auth.implement;

import com.clubber.ClubberServer.domain.auth.domain.UserRefreshToken;
import com.clubber.ClubberServer.domain.auth.repository.UserRefreshTokenRepository;
import com.clubber.ClubberServer.global.jwt.vo.TokenVO;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.global.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserTokenAppender {
    private final UserRefreshTokenRepository userRefreshTokenRepository;

    private final JwtTokenUtil jwtTokenUtil;

    public TokenVO saveUserToken(User user) {
        String accessToken = jwtTokenUtil.generateAccessToken(user.getId(), user.getRole());
        String refreshToken = jwtTokenUtil.generateRefreshToken(user.getId());

        UserRefreshToken userRefreshToken = UserRefreshToken.of(user.getId(), refreshToken,
                jwtTokenUtil.getRefreshTokenTTlSecond());
        userRefreshTokenRepository.save(userRefreshToken);
        return new TokenVO(accessToken, refreshToken);
    }

    public void deleteRefreshTokenById(Long id) {
        userRefreshTokenRepository.deleteById(id);
    }
}
