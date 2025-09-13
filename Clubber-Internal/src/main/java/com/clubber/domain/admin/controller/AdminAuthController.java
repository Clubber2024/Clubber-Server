package com.clubber.domain.admin.controller;

import com.clubber.domain.admin.dto.InternalAdminLoginRequest;
import com.clubber.domain.admin.dto.InternalTokenResponse;
import com.clubber.domain.admin.service.AdminAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/auth")
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public InternalTokenResponse login(@RequestBody InternalAdminLoginRequest request) {
        return adminAuthService.login(request);
    }
}
