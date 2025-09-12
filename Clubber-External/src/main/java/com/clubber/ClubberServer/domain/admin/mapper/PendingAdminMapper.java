package com.clubber.ClubberServer.domain.admin.mapper;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.domain.domains.club.domain.Club;
import org.springframework.stereotype.Component;

@Component
public class PendingAdminMapper {

    public Admin toAdmin(PendingAdminInfo pendingAdminInfo, Club club) {
        return Admin.builder()
                .username(pendingAdminInfo.getUsername())
                .password(pendingAdminInfo.getPassword())
                .contact(pendingAdminInfo.getContact())
                .email(pendingAdminInfo.getEmail())
                .club(club)
                .build();
    }

    public Club toClub(PendingAdminInfo pendingAdminInfo, String clubName) {
        return Club.builder()
                .name(clubName)
                .clubType(pendingAdminInfo.getClubType())
                .college(pendingAdminInfo.getCollege())
                .department(pendingAdminInfo.getDepartment())
                .division(pendingAdminInfo.getDivision())
                .hashtag(pendingAdminInfo.getHashtag())
                .build();
    }
}
