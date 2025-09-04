package com.clubber.ClubberInternal.domain.admin.service;

import com.clubber.ClubberInternal.domain.admin.dto.InternalAdminLoginRequest;
import com.clubber.ClubberInternal.domain.admin.dto.InternalTokenResponse;
import com.clubber.ClubberInternal.domain.internal.domain.InternalAdmin;
import com.clubber.ClubberInternal.domain.internal.repository.InternalAdminRepository;
import com.clubber.ClubberInternal.global.jwt.AccountRole;
import com.clubber.ClubberInternal.global.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminAuthService {
    private final PasswordEncoder encoder;
    private final InternalAdminRepository internalAdminRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public InternalTokenResponse login(InternalAdminLoginRequest request) {
        InternalAdmin internalAdmin = internalAdminRepository.findByUsername(request.username());
        String password = internalAdmin.getPassword();
        if (!encoder.matches(request.password(), password)) {
            throw new RuntimeException("로그인 오류");
        }
        String accessToken = jwtTokenUtil.generateAccessToken(internalAdmin.getId(), AccountRole.SUPER_ADMIN);
        String refreshToken = jwtTokenUtil.generateRefreshToken(internalAdmin.getId());
        return new InternalTokenResponse(accessToken, refreshToken);
    }

}
