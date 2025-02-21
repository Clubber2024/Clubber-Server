package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @Column(columnDefinition = "DATETIME(0)")
    private LocalDateTime startAt;

    @NotNull
    @Column(columnDefinition = "DATETIME(0)")
    private LocalDateTime endAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Semester semester;

    @NotNull
    private int year;

    @NotNull
    @Column(length = 100)
    private String title;

    @NotNull
    @Column(length = 2000)
    private String content;

    private String everytimeUrl;

    private Long totalView;

    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    @OneToMany(mappedBy = "recruit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecruitImage> recruitImages = new ArrayList<>();

    public void delete() {
        this.isDeleted = true;
    }

    public void increaseTotalview() {
        this.totalView++;
    }

    public void updateRecruitPage(String title, String content, String everytimeUrl) {
        this.title = title;
        this.content = content;
        this.everytimeUrl = everytimeUrl;
    }


    @Builder
    private Recruit(Long id, LocalDateTime startAt, LocalDateTime endAt, Semester semester, int year,
        String title,
        String content, String everytimeUrl, Long totalView,
        Club club, List<RecruitImage> recruitImages) {
        this.id = id;
        this.startAt = startAt;
        this.endAt = endAt;
        this.semester = semester;
        this.year = year;
        this.title = title;
        this.content = content;
        this.everytimeUrl = everytimeUrl;
        this.totalView = totalView;
        this.club = club;
        this.recruitImages = recruitImages;
    }

    public static Recruit of(Club club, PostRecruitRequest request) {
        return Recruit.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .everytimeUrl(request.getEverytimeUrl())
            .totalView(0L)
            .club(club)
            .build();
    }
}
