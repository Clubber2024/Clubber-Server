package com.clubber.ClubberServer.domain.admin.implement;

import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.PendingAdminInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PendingAdminInfoReader {
    private final PendingAdminInfoRepository pendingAdminInfoRepository;

    public void checkAlreadyApproved(String clubName) {
        if (pendingAdminInfoRepository.existsPendingAdminInfoByClubNameAndIsApproved(clubName, true)) {
            throw new RuntimeException("승인 내역 확인 : 이미 승인된 동아리입니다");
        }
    }

    public PendingAdminInfo getById(Long id) {
        return pendingAdminInfoRepository.findById(id)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);
    }
}
