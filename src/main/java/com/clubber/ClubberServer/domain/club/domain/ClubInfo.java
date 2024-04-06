package com.clubber.ClubberServer.domain.club.domain;

import jakarta.persistence.*;
import lombok.Builder;

public class ClubInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String instagram;

    private String leader;

    private Long room;

    private Long totalView;


    @Builder
    private ClubInfo(Long id,String instagram,String leader,Long room,Long totalView){
        this.id=id;
        this.instagram=instagram;
        this.leader=leader;
        this.room=room;
        this.totalView=totalView;
    }

}
