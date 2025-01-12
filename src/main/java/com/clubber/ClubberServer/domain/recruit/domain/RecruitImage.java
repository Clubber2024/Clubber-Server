package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.global.vo.image.ImageVO;

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

    @Enumerated
    private ImageVO imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_id", nullable = false)
    private Recruit recruit;

    private Long orderNum;

    private boolean isDeleted=false;

    @Builder
    private RecruitImage(Long id, ImageVO imageUrl,Recruit recruit){
        this.id=id;
        this.imageUrl=imageUrl;
        this.recruit=recruit;
    }

    public static RecruitImage of(ImageVO imageUrl,Recruit recruit){
        return RecruitImage.builder()
                .imageUrl(imageUrl)
                .recruit(recruit)
                .build();
    }

    public void updateStatus(){this.isDeleted=true;}

    public void updateOrderNum(Long orderNum){this.orderNum=orderNum;}

}
