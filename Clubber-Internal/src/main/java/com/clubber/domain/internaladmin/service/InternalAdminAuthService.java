package com.clubber.domain.internaladmin.service;

import com.clubber.domain.internaladmin.dto.InternalAdminLoginRequest;
import com.clubber.domain.internaladmin.dto.InternalAdminTokenResponse;
import com.clubber.domain.domains.admin.exception.AdminLoginFailedException;
import com.clubber.domain.internaladmin.domain.InternalAdmin;
import com.clubber.domain.internaladmin.repository.InternalAdminRepository;
import com.clubber.global.jwt.AccountRole;
import com.clubber.global.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalAdminAuthService {
    private final PasswordEncoder encoder;
    private final InternalAdminRepository internalAdminRepository;
    private final JwtTokenUtil jwtTokenUtil;

    public InternalAdminTokenResponse login(InternalAdminLoginRequest request) {
        InternalAdmin internalAdmin = internalAdminRepository.findByUsername(request.username());
        String password = internalAdmin.getPassword();
        if (!encoder.matches(request.password(), password)) {
            throw AdminLoginFailedException.EXCEPTION;
        }
        String accessToken = jwtTokenUtil.generateAccessToken(internalAdmin.getId(), AccountRole.SUPER_ADMIN);
        return new InternalAdminTokenResponse(accessToken);
    }
}
