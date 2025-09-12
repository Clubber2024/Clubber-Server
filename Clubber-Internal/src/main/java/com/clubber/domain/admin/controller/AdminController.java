package com.clubber.domain.admin.controller;

import com.clubber.domain.admin.dto.PendingAdminInfoResponse;
import com.clubber.domain.admin.service.InternalClubApproveService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/admins")
@RequiredArgsConstructor
public class AdminController {
    private final InternalClubApproveService internalClubApproveService;
    @GetMapping
    public List<PendingAdminInfoResponse> getNotApprovedPendingAdmins() {
        return internalClubApproveService.getPendingAdminInfos();
    }
}
