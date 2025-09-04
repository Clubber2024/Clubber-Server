package com.clubber.ClubberInternal.domain.admin.service;

import com.clubber.ClubberInternal.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberInternal.domain.admin.dto.PendingAdminInfoResponse;
import com.clubber.ClubberInternal.domain.admin.repository.PendingAdminInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InternalClubApproveService {
    private final PendingAdminInfoRepository pendingAdminInfoRepository;

    public List<PendingAdminInfoResponse> getPendingAdminInfos() {
        List<PendingAdminInfo> pendingAdminInfos = pendingAdminInfoRepository.findByIsApproved(false);
        return pendingAdminInfos.stream().map(PendingAdminInfoResponse::of).toList();
    }
}
