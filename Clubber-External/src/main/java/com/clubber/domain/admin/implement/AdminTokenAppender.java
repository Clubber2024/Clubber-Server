package com.clubber.domain.admin.implement;

import com.clubber.domain.admin.domain.Admin;
import com.clubber.domain.admin.domain.AdminRefreshToken;
import com.clubber.domain.admin.repository.AdminRefreshTokenRepository;
import com.clubber.global.jwt.vo.TokenVO;
import com.clubber.global.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminTokenAppender {
    private final AdminRefreshTokenRepository adminRefreshTokenRepository;

    private final JwtTokenUtil jwtTokenUtil;

    public TokenVO createAdminsToken(Admin admin) {
        String accessToken = jwtTokenUtil.generateAccessToken(admin.getId(), admin.getAccountRole());
        String refreshToken = jwtTokenUtil.generateRefreshToken(admin.getId());

        AdminRefreshToken adminRefreshToken = AdminRefreshToken.of(admin.getId(), refreshToken,
                jwtTokenUtil.getRefreshTokenTTlSecond());
        adminRefreshTokenRepository.save(adminRefreshToken);
        return new TokenVO(accessToken, refreshToken);
    }

    public void deleteRefreshTokenById(Long id) {
        adminRefreshTokenRepository.deleteById(id);
    }
}
