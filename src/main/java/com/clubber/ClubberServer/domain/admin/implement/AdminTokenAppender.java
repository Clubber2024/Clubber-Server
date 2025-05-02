package com.clubber.ClubberServer.domain.admin.implement;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.auth.vo.TokenVO;
import com.clubber.ClubberServer.domain.admin.domain.AdminRefreshToken;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import com.clubber.ClubberServer.global.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminTokenAppender {
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenUtil jwtTokenUtil;

    public TokenVO createAdminsToken(Admin admin) {
        String accessToken = jwtTokenUtil.generateAccessToken(admin.getId(), admin.getAccountRole());
        String refreshToken = jwtTokenUtil.generateRefreshToken(admin.getId());

        AdminRefreshToken adminRefreshToken = AdminRefreshToken.of(admin.getId(), refreshToken,
                jwtTokenUtil.getRefreshTokenTTlSecond());
        refreshTokenRepository.save(adminRefreshToken);
        return new TokenVO(accessToken, refreshToken);
    }

    public void deleteRefreshTokenById(Long id) {
        refreshTokenRepository.deleteById(id);
    }
}
