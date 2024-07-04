package com.clubber.ClubberServer.domain.admin.controller;

import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginRequest;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageResponse;
import com.clubber.ClubberServer.domain.admin.service.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins")
@Tag(name = "[동아리 계정 API]")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public CreateAdminsLoginResponse createAdminsLogin(@RequestBody @Valid CreateAdminsLoginRequest loginRequest){
        return adminService.createAdminsLogin(loginRequest);
    }

    @PatchMapping("/change-page")
    public UpdateClubPageResponse updateAdminsPage(@RequestBody @Valid UpdateClubPageRequest pageRequest){
        return adminService.updateAdminsPage(pageRequest);
    }


}
