package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.CreateUsersLoginRequest;
import com.clubber.ClubberServer.domain.admin.exception.AdminLoginFailedException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final PasswordEncoder encoder;

    public void createUserLogin(CreateUsersLoginRequest loginRequest){
        Admin admin = adminRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> AdminLoginFailedException.EXCEPTION);

        if(!encoder.matches(loginRequest.getPassword(), admin.getPassword()))
            throw AdminLoginFailedException.EXCEPTION;
    }
}
