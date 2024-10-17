package com.clubber.ClubberServer.domain.admin.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateClubPageRequest {
    private String imageKey;
    private String introduction;
    private String instagram;
    private String activity;
    private String leader;
    private Long room;

}