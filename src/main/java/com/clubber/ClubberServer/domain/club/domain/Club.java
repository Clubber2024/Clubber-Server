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

    private List<String> hashtag= new ArrayList<>();;

    private String division; //분과 (중앙동아리)

    private String college; //단과대 (소모임)

    private String department; //학과 (소모임)

    @OneToMany(mappedBy = "club")
    private List<Review> reviews=new ArrayList<>();

    private String content;

    @OneToOne(mappedBy="club")
    private Introduction introduction;

    @Builder
    public Club(Long id, String name, String clubType,List<String> hashtag,String division, String college, String department,List<Review> reviews,String content,Introduction introduction) {
        this.id = id;
        this.name=name;
        this.clubType=clubType;
        this.hashtag=hashtag;
        this.division = division;
        this.college = college;
        this.department = department;
        this.reviews=reviews !=null ?reviews: new ArrayList<>();
        this.content=content;
        this.introduction=introduction;
    }
}