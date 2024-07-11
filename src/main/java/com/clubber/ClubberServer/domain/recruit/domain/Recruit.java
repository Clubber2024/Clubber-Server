package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;


    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    private RecruitType recruitType;

    private int year;

    private int semester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    @Builder
    private Recruit(Long id,String title,String content,RecruitType recruitType,int year,int semester,Club club){
        this.id=id;
        this.title=title;
        this.content=content;
        this.recruitType=recruitType;
        this.year=year;
        this.semester=semester;
        this.club=club;
    }
}
