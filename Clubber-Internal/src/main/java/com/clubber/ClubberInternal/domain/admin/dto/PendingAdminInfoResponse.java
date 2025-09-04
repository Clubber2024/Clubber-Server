package com.clubber.ClubberInternal.domain.admin.dto;

import com.clubber.ClubberInternal.domain.admin.domain.Contact;
import com.clubber.ClubberInternal.domain.admin.domain.PendingAdminInfo;
import lombok.Builder;

import static com.clubber.ClubberInternal.global.consts.ClubberStatic.IMAGE_SERVER;

@Builder
public record PendingAdminInfoResponse(Long id,
                                       String username,
                                       String clubType,
                                       String college,
                                       String department,
                                       String division,
                                       String hashtag,
                                       String clubName,
                                       String email,
                                       Contact contact,
                                       String imageForApproval,
                                       boolean isApproved) {
    public static PendingAdminInfoResponse of(PendingAdminInfo pendingAdminInfo) {
        return PendingAdminInfoResponse.builder()
                .id(pendingAdminInfo.getId())
                .username(pendingAdminInfo.getUsername())
                .clubType(pendingAdminInfo.getClubType().getTitle())
                .college(pendingAdminInfo.getCollege().getTitle())
                .division(pendingAdminInfo.getDivision().getTitle())
                .hashtag(pendingAdminInfo.getHashtag().getTitle())
                .department(pendingAdminInfo.getDepartment().getTitle())
                .clubName(pendingAdminInfo.getClubName())
                .email(pendingAdminInfo.getEmail())
                .contact(pendingAdminInfo.getContact())
                .imageForApproval(IMAGE_SERVER + pendingAdminInfo.getImageForApproval())
                .isApproved(pendingAdminInfo.isApproved())
                .build();

    }
}
