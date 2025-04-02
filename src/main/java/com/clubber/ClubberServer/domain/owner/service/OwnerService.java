package com.clubber.ClubberServer.domain.owner.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.admin.exception.AdminNotFoundException;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.repository.PendingAdminInfoRepository;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;

@Service
@RequiredArgsConstructor
@Transactional
public class OwnerService {
    private final PendingAdminInfoRepository pendingAdminInfoRepository;
    private final AdminRepository adminRepository;
    private final ClubRepository clubRepository;

    public void approveClubAdmin(Long id) {
        PendingAdminInfo pendingAdminInfo = pendingAdminInfoRepository.findById(id)
                .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

        String clubName = pendingAdminInfo.getClubName();
        if (pendingAdminInfoRepository.existsByClubNameAndApproved(clubName, true)) {
            throw new RuntimeException("승인 내역 확인 : 이미 승인된 동아리입니다");
        }

        Optional<Club> clubOptional = clubRepository.findClubByNameAndIsDeleted(clubName, false);
        if (clubOptional.isPresent()) {
            //중앙동아리 or 공식단체
            Club club = clubOptional.get();
            Admin admin = adminRepository.findByClubAndAccountState(club, ACTIVE)
                    .orElseThrow(() -> AdminNotFoundException.EXCEPTION);

            admin.updateUsername(admin.getUsername());
            admin.updatePassword(pendingAdminInfo.getPassword());
            admin.updateContact(pendingAdminInfo.getContact());
            admin.updateEmail(pendingAdminInfo.getEmail());
        } else {
            //일반 동아리 & 소모임
            if (pendingAdminInfo.getClubType() == ClubType.CENTER) {
                throw new RuntimeException("동아리 이름 확인 필요 : 중앙 동아리는 이미 존재해야 합니다.");
            }
            Club club = Club.builder()
                    .name(clubName)
                    .clubType(pendingAdminInfo.getClubType())
                    .build();
            Club savedClub = clubRepository.save(club);

            Admin admin = Admin.builder()
                    .username(pendingAdminInfo.getUsername())
                    .password(pendingAdminInfo.getPassword())
                    .contact(pendingAdminInfo.getContact())
                    .email(pendingAdminInfo.getEmail())
                    .club(savedClub)
                    .build();
            adminRepository.save(admin);
        }
    }
}
