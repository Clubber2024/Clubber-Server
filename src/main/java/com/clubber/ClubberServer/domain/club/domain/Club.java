package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.fasterxml.jackson.databind.ser.Serializers.Base;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String clubType;

    private String introduction;

    private String hashtag;

    private String division; //분과 (중앙동아리)

    private String college; //단과대 (소모임)

    private String department; //학과 (소모임)

    private String imageUrl;


    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "clubInfo_id")
    private ClubInfo clubInfo;

    @Builder
    private Club(Long id, String name, String clubType,String introduction,String hashtag,String division, String college, String department,String imageUrl,ClubInfo clubInfo) {
        this.id = id;
        this.name=name;
        this.clubType=clubType;
        this.introduction=introduction;
        this.hashtag=hashtag;
        this.division = division;
        this.college = college;
        this.department = department;
        this.imageUrl=imageUrl;
        this.clubInfo=clubInfo;
    }
}