package com.clubber.ClubberServer.domain.admin.controller;

import com.clubber.ClubberServer.domain.admin.dto.CreateUsersLoginRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateUsersLoginResponse;
import com.clubber.ClubberServer.domain.admin.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins")
@Tag(name = "[동아리 계정 API]")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public CreateUsersLoginResponse createUsersLogin(@RequestBody CreateUsersLoginRequest loginRequest){
        return adminService.createUserLogin(loginRequest);
    }
}
