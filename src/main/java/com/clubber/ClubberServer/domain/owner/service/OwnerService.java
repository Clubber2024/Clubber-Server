package com.clubber.ClubberServer.domain.owner.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.admin.implement.PendingAdminInfoAppender;
import com.clubber.ClubberServer.domain.admin.implement.PendingAdminInfoReader;
import com.clubber.ClubberServer.domain.admin.mapper.PendingAdminMapper;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.repository.PendingAdminInfoRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerService {
    private final PendingAdminInfoRepository pendingAdminInfoRepository;
    private final ClubRepository clubRepository;
    private final AdminReader adminReader;
    private final PendingAdminInfoAppender pendingAdminInfoAppender;
    private final PendingAdminInfoReader pendingAdminInfoReader;

    public void approveClubAdmin(Long id) {
        PendingAdminInfo pendingAdminInfo = pendingAdminInfoReader.getById(id);

        String clubName = pendingAdminInfo.getClubName();
        pendingAdminInfoReader.checkAlreadyApproved(clubName);
        pendingAdminInfoAppender.upsertByApprove(pendingAdminInfo, clubName);
    }
}
