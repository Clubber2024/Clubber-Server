package com.clubber.ClubberServer.domain.club.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class OneClubInfoDto {
    private String instagram;
    private String leader;
    private Long room;
    private Long totalView;
    private String activity;


    @Builder
    public OneClubInfoDto(String instagram,String leader,Long room,Long totalView,String activity){
        this.instagram=instagram;
        this.leader=leader;
        this.room=room;
        this.totalView=totalView;
        this.activity=activity;
    }
}
