package com.clubber.domain.admin.implement;

import com.clubber.domain.domains.admin.domain.PendingAdminInfo;
import com.clubber.domain.domains.admin.exception.AdminNotFoundException;
import com.clubber.domain.admin.repository.PendingAdminInfoRepository;
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
