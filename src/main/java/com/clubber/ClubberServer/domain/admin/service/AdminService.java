package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordResponse;
import com.clubber.ClubberServer.domain.admin.exception.AdminLoginFailedException;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.user.domain.RefreshTokenEntity;
import com.clubber.ClubberServer.domain.user.exception.RefreshTokenExpiredException;
import com.clubber.ClubberServer.domain.user.repository.RefreshTokenRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
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

    @Transactional
    public UpdateAdminsPasswordResponse updateAdminsPassword(UpdateAdminsPasswordRequest updateAdminsPasswordRequest) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Admin admin = adminRepository.findById(currentUserId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        String rawPassword = updateAdminsPasswordRequest.getPassword();
        admin.updatePassword(encoder.encode(rawPassword));
        return UpdateAdminsPasswordResponse.of(admin);
    }

    @Transactional
    public void logout() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        refreshTokenRepository.deleteById(currentUserId);
    }

    @Transactional
    public void tokenRefresh(String refreshToken){
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByRefreshToken(
                        refreshToken)
                .orElseThrow(() -> RefreshTokenExpiredException.EXCEPTION);
        Long adminId = jwtTokenProvider.parseRefreshToken(refreshTokenEntity.getRefreshToken());
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
    }

}
