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
import org.hibernate.annotations.Where;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted = false")
public class Recruit extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String content;


    private Long totalView;

    private boolean deleted = Boolean.FALSE;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    public void updateTotalview() {
        this.totalView++;
    }


    @Builder
    private Recruit(Long id,String title,String content,Long totalView,Club club,boolean deleted){
        this.id=id;
        this.title=title;
        this.content=content;
        this.totalView=totalView;
        this.club=club;
        this.deleted=deleted;
    }

    public static Recruit of(Club club, PostRecruitRequest request){
        return Recruit.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .totalView(0L)
                .club(club)
                .deleted(Boolean.FALSE)
                .build();
    }
}
