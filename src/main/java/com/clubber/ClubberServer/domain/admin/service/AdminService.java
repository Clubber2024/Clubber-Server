package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.admin.exception.AdminLoginFailedException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public CreateAdminsLoginResponse createAdminsLogin(CreateAdminsLoginRequest loginRequest){
        Admin admin = adminRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> AdminLoginFailedException.EXCEPTION);

        if(!encoder.matches(loginRequest.getPassword(), admin.getPassword()))
            throw AdminLoginFailedException.EXCEPTION;
        return createAdminsToken(admin);
    }

    private CreateAdminsLoginResponse createAdminsToken(Admin admin){
        String accessToken = jwtTokenProvider.generateAccessToken(admin);
        String refreshToken = jwtTokenProvider.generateRefreshToken(admin.getId());
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.of(admin.getId(), refreshToken,
                jwtTokenProvider.getRefreshTokenTTlSecond());
        RefreshTokenEntity savedRefreshToken = refreshTokenRepository.save(refreshTokenEntity);
        return CreateAdminsLoginResponse.of(admin, accessToken, savedRefreshToken.getRefreshToken());
    }

}