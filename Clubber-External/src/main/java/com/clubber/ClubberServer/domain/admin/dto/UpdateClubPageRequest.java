package com.clubber.ClubberServer.domain.admin.dto;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UpdateClubPageRequest {

    private String imageKey;

    @Size(max = 1000, message = "최대 1000자까지 작성 가능합니다.")
    private String introduction;

    private String instagram;

    private String youtube;

    @Size(max = 1500, message = "최대 1500자까지 작성 가능합니다.")
    private String activity;

    private String leader;

    private Long room;
}