package com.clubber.ClubberServer.domain.club.domain;

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
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String clubType;

    private String hashtag;

    private String division; //분과 (중앙동아리)

    private String college; //단과대 (소모임)

    private String department; //학과 (소모임)


    @OneToOne
    @JoinColumn(name = "clubInfo_id")
    private ClubInfo clubInfo;

    @Builder
    private Club(Long id, String name, String clubType,String hashtag,String division, String college, String department,ClubInfo clubInfo) {
        this.id = id;
        this.name=name;
        this.clubType=clubType;
        this.hashtag=hashtag;
        this.division = division;
        this.college = college;
        this.department = department;
        this.clubInfo=clubInfo;
    }
}