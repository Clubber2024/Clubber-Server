package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.ClubberServer.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubInfo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String instagram;

    private String leader;

    private Long room;

    private Long totalView;

    @Column(length = 1500)
    private String activity;

    public void increaseTotalView(){
        this.totalView++;
    }

    public void updateClubInfo(String instagram,String leader,String activity,Long room) {
        this.instagram=instagram;
        this.leader=leader;
        this.activity=activity;
        this.room=room;
    }
    @Builder
    private ClubInfo(Long id,String instagram,String leader,Long room,Long totalView,String activity){
        this.id=id;
        this.instagram=instagram;
        this.leader=leader;
        this.room=room;
        this.totalView=totalView;
        this.activity=activity;
    }

}
