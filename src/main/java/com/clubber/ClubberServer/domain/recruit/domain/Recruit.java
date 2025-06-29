package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.ClubberServer.domain.calender.entity.Calender;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {@Index(name = "idx_recruit_club_id_is_deleted", columnList = "club_id, is_deleted")})
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

    private Long totalView = 0L;

    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    @OneToOne
    @JoinColumn(name = "calender_id")
    private Calender calender;

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
}
