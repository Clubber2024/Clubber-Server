package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 100)
    private String title;

    @NotNull
    @Column(length = 2000)
    private String content;

    private Long totalView;

    private boolean isDeleted=false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    @OneToMany(mappedBy = "recruit",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecruitImage> recruitImages = new ArrayList<>();

    public void delete(){
        this.isDeleted=true;
    }

    public void increaseTotalview(){
        this.totalView++;
    }

    public void updateRecruitPage(String title, String content){
        this.title=title;
        this.content=content;
    }


    @Builder
    private Recruit(Long id,String title,String content,Long totalView,Club club,List<RecruitImage> recruitImages){
        this.id=id;
        this.title=title;
        this.content=content;
        this.totalView=totalView;
        this.club=club;
        this.recruitImages=recruitImages;
    }

    public static Recruit of(Club club, PostRecruitRequest request){
        return Recruit.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .totalView(0L)
                .club(club)
                .build();
    }
}
