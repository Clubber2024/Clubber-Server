package com.clubber.domain.internaladmin.controller;

import com.clubber.domain.internaladmin.dto.InternalAdminLoginRequest;
import com.clubber.domain.internaladmin.dto.InternalAdminTokenResponse;
import com.clubber.domain.internaladmin.service.InternalAdminAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/auth")
public class AdminAuthController {

    private final InternalAdminAuthService internalAdminAuthService;

    @PostMapping("/login")
    public InternalAdminTokenResponse login(@RequestBody InternalAdminLoginRequest request) {
        return internalAdminAuthService.login(request);
    }
}
