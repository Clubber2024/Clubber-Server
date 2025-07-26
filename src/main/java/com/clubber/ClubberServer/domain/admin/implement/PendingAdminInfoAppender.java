package com.clubber.ClubberServer.domain.admin.implement;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.admin.mapper.PendingAdminMapper;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class PendingAdminInfoAppender {
    private final PendingAdminMapper pendingAdminMapper;
    private final ClubRepository clubRepository;
    private final AdminRepository adminRepository;

    public Club registerClub(PendingAdminInfo pendingAdminInfo, String clubName) {
        if (pendingAdminInfo.getClubType() == ClubType.CENTER) {
            throw new RuntimeException("동아리 이름 확인 필요 : 중앙 동아리는 이미 존재해야 합니다.");
        }

        Club club = pendingAdminMapper.toClub(pendingAdminInfo, clubName);
        return clubRepository.save(club);
    }

    public void registerAdmin(PendingAdminInfo pendingAdminInfo, Club savedClub) {
        Admin admin = pendingAdminMapper.toAdmin(pendingAdminInfo, savedClub);
        adminRepository.save(admin);
    }

    public void updateAdminByApprove(Admin admin, PendingAdminInfo pendingAdminInfo) {
        admin.updateUsername(pendingAdminInfo.getUsername());
        admin.updatePassword(pendingAdminInfo.getPassword());
        admin.updateContact(pendingAdminInfo.getContact());
        admin.updateEmail(pendingAdminInfo.getEmail());
    }
}
