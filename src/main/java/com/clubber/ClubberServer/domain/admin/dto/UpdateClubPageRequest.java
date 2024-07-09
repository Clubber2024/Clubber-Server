package com.clubber.ClubberServer.domain.admin.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateClubPageRequest {
    private String imageUrl;
    private String introduction;
    private String instagram;
    private String activity;
    private String leader;
    private Long room;

}