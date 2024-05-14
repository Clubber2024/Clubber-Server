package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SimpleCenterDto { //clubId,clubName을 반환함
    private Long clubId;
    private String clubName;
    private String introduction; //동아리에 대한 설명도 들처가야함

    @Builder
    public SimpleCenterDto(Long clubId,String clubName,String introduction){
        this.clubId=clubId;
        this.clubName=clubName;
        this.introduction=introduction;
    }

}