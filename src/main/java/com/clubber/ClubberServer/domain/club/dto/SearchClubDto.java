package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SearchClubDto {
    private String clubType;
    private String division;
    private String department;
    private Long clubId;
    private String imageUrl;
    private String clubName;
    private String introduction; //동아리에 대한 설명도 들처가야함

    @Builder
    public SearchClubDto(String clubType,String division,String department,Long clubId,String imageUrl,String clubName,String introduction){
        this.clubType=clubType;
        this.division=division;
        this.department=department;
        this.clubId=clubId;
        this.imageUrl=imageUrl;
        this.clubName=clubName;
        this.introduction=introduction;
    }
}
