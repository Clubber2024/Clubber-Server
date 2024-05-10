package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class OneClubDto {
    private Long clubId;
    private String clubName;
    private String clubType;
    private String hashTag;
    private String division;
    private String college;
    private String department;
    private OneClubInfoDto clubInfo;

    @Builder
    public OneClubDto(Long clubId,String clubName,String clubType,String hashTag,String division,String college,String department,OneClubInfoDto clubInfo)
    {
        this.clubId=clubId;
        this.clubName=clubName;
        this.clubType=clubType;
        this.hashTag=hashTag;
        this.division=division;
        this.college=college;
        this.department=department;
        this.clubInfo=clubInfo;

    }
}
