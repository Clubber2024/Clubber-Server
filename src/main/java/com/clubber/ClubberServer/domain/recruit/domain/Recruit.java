package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitPageRequest;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.NULL_CONTENT;
import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.PENDING;
import static org.reflections.util.ConfigurationBuilder.build;

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


    private String imageUrl;

    private Long totalView;

//    @NotNull
//    @JdbcTypeCode(SqlTypes.VARCHAR)
//    @Enumerated(EnumType.STRING)
//    private RecruitType recruitType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    @Builder
    private Recruit(Long id,String title,String content,String imageUrl,Long totalView,Club club){
        this.id=id;
        this.title=title;
        this.content=content;
        this.imageUrl=imageUrl;
        this.totalView=totalView;
        this.club=club;
    }

    public static Recruit of(Club club,PostRecruitPageRequest request){
        return Recruit.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .totalView(0L)
                .club(club)
                .build();
    }
}