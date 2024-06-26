package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class OneClubDto {
    private Long clubId;
    private String clubName;
    private String clubType;
    private String introduction;
    private String hashTag;
    private String division;
    private String college;
    private String department;
    private String imageUrl;
    private OneClubInfoDto clubInfo;

    @Builder
    public OneClubDto(Long clubId,String clubName,String clubType,String introduction,String hashTag,String division,String college,String department,String imageUrl,OneClubInfoDto clubInfo)
    {
        this.clubId=clubId;
        this.clubName=clubName;
        this.clubType=clubType;
        this.introduction=introduction;
        this.hashTag=hashTag;
        this.division=division;
        this.college=college;
        this.department=department;
        this.imageUrl=imageUrl;
        this.clubInfo=clubInfo;

    }
}
