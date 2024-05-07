package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class SimpleCenterDto { //clubId,clubName을 반환함
    private Long clubId;
    private String clubName;

    @Builder
    public SimpleCenterDto(Long clubId,String clubName){
        this.clubId=clubId;
        this.clubName=clubName;
    }

}
