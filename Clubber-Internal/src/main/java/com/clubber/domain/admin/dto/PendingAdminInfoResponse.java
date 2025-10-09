package com.clubber.domain.admin.dto;

import com.clubber.domain.domains.admin.domain.Contact;
import com.clubber.domain.domains.admin.domain.PendingAdminInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

import static com.clubber.common.consts.ClubberStatic.IMAGE_SERVER;

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
                                       boolean isApproved,
                                       @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd", timezone = "Asia/Seoul")
                                       LocalDateTime createdAt) {
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
                .createdAt(pendingAdminInfo.getCreatedAt())
                .build();

    }
}
