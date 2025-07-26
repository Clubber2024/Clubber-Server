package com.clubber.ClubberServer.domain.owner.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.admin.mapper.PendingAdminMapper;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.repository.PendingAdminInfoRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerService {
    private final PendingAdminInfoRepository pendingAdminInfoRepository;
    private final AdminRepository adminRepository;
    private final ClubRepository clubRepository;
    private final AdminReader adminReader;
    private final PendingAdminMapper pendingAdminMapper;

    public void approveClubAdmin(Long id) {
        PendingAdminInfo pendingAdminInfo = pendingAdminInfoRepository.findById(id)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        String clubName = pendingAdminInfo.getClubName();
        if (pendingAdminInfoRepository.existsPendingAdminInfoByClubNameAndIsApproved(clubName, true)) {
            throw new RuntimeException("승인 내역 확인 : 이미 승인된 동아리입니다");
        }

        Optional<Club> clubOptional = clubRepository.findClubByNameAndIsDeleted(clubName, false);
        if (clubOptional.isPresent()) {
            //중앙동아리 or 공식단체
            Admin admin = adminReader.getAdminByClub(clubOptional.get());
            updateAdminByApprove(admin, pendingAdminInfo);
        } else {
            //일반 동아리 & 소모임
            if (pendingAdminInfo.getClubType() == ClubType.CENTER) {
                throw new RuntimeException("동아리 이름 확인 필요 : 중앙 동아리는 이미 존재해야 합니다.");
            }

            Club savedClub = registerClub(pendingAdminInfo, clubName);
            registerAdmin(pendingAdminInfo, savedClub);
        }
    }

    private static void updateAdminByApprove(Admin admin, PendingAdminInfo pendingAdminInfo) {
        admin.updateUsername(pendingAdminInfo.getUsername());
        admin.updatePassword(pendingAdminInfo.getPassword());
        admin.updateContact(pendingAdminInfo.getContact());
        admin.updateEmail(pendingAdminInfo.getEmail());
    }

    private Club registerClub(PendingAdminInfo pendingAdminInfo, String clubName) {
        Club club = pendingAdminMapper.toClub(pendingAdminInfo, clubName);
        return clubRepository.save(club);
    }

    private void registerAdmin(PendingAdminInfo pendingAdminInfo, Club savedClub) {
        Admin admin = pendingAdminMapper.toAdmin(pendingAdminInfo, savedClub);
        adminRepository.save(admin);
    }
}
