package com.clubber.domain.owner.service;

import com.clubber.domain.domains.admin.domain.PendingAdminInfo;
import com.clubber.domain.admin.implement.PendingAdminInfoManager;
import com.clubber.domain.admin.implement.PendingAdminInfoReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerService {
    private final PendingAdminInfoManager pendingAdminInfoManager;
    private final PendingAdminInfoReader pendingAdminInfoReader;

    public void approveClubAdmin(Long id) {
        PendingAdminInfo pendingAdminInfo = pendingAdminInfoReader.getById(id);

        String clubName = pendingAdminInfo.getClubName();
        pendingAdminInfoReader.checkAlreadyApproved(clubName);
        pendingAdminInfoManager.upsertByApprove(pendingAdminInfo, clubName);
    }
}
