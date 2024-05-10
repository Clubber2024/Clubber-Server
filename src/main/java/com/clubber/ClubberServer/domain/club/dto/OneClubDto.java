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
    private OneClubInfoDto oneClubInfoDto;

    @Builder
    public OneClubDto(Long clubId,String clubName,String clubType,String hashTag,String division,String college,String department,OneClubInfoDto oneClubInfoDto)
    {
        this.clubId=clubId;
        this.clubName=clubName;
        this.clubType=clubType;
        this.hashTag=hashTag;
        this.division=division;
        this.college=college;
        this.department=department;
        this.oneClubInfoDto=oneClubInfoDto;

    }
}
