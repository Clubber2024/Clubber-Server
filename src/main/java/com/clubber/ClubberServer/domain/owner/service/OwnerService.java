package com.clubber.ClubberServer.domain.owner.service;

import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.admin.implement.PendingAdminInfoAppender;
import com.clubber.ClubberServer.domain.admin.implement.PendingAdminInfoReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerService {
    private final PendingAdminInfoAppender pendingAdminInfoAppender;
    private final PendingAdminInfoReader pendingAdminInfoReader;

    public void approveClubAdmin(Long id) {
        PendingAdminInfo pendingAdminInfo = pendingAdminInfoReader.getById(id);

        String clubName = pendingAdminInfo.getClubName();
        pendingAdminInfoReader.checkAlreadyApproved(clubName);
        pendingAdminInfoAppender.upsertByApprove(pendingAdminInfo, clubName);
    }
}
