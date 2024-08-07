package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id", nullable = false)
    private Recruit recruit;

    @Builder
    private RecruitImage(Long id,String imageUrl,Recruit recruit){
        this.id=id;
        this.imageUrl=imageUrl;
        this.recruit=recruit;
    }

    public static RecruitImage of(String imageUrl,Recruit recruit){
        return RecruitImage.builder()
                .imageUrl(imageUrl)
                .recruit(recruit)
                .build();
    }

}
