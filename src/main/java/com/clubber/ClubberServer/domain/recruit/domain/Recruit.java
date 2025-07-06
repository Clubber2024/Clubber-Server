package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.ClubberServer.domain.calendar.entity.Calendar;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(indexes = {
    @Index(name = "idx_recruit_club_id_is_deleted", columnList = "club_id, is_deleted")})
public class Recruit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "DATETIME(0)")
    private LocalDateTime startAt;

    @Column(columnDefinition = "DATETIME(0)")
    private LocalDateTime endAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private RecruitType recruitType;

//    @NotNull
//    private int year;

    @NotNull
    @Column(length = 100)
    private String title;

    @NotNull
    @Column(length = 2000)
    private String content;

    private String applyLink;

    private Long totalView = 0L;

    private boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    @OneToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @OneToMany(mappedBy = "recruit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RecruitImage> recruitImages = new ArrayList<>();

    public void delete() {
        this.isDeleted = true;
    }

    public void increaseTotalview() {
        this.totalView++;
    }

    public void updateRecruitPage(String title, String content, String applyLink,
        LocalDateTime startAt, LocalDateTime endAt) {
        this.title = title;
        this.content = content;
        this.applyLink = applyLink;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public boolean isCalendarLinked() {
        return this.calendar != null;
    }

    public void unlinkCalendar() {
        this.calendar = null;
    }

    public void linkCalendar(Calendar calendar) {
        this.calendar = calendar;
    }


    @Builder
    private Recruit(Long id, LocalDateTime startAt, LocalDateTime endAt, String title,
        RecruitType recruitType, String content, String applyLink, Long totalView, Club club,
        List<RecruitImage> recruitImages) {
        this.id = id;
        this.startAt = startAt;
        this.endAt = endAt;
        this.title = title;
        this.recruitType = recruitType;
        this.content = content;
        this.applyLink = applyLink;
        this.totalView = totalView;
        this.club = club;
        this.recruitImages = recruitImages;
    }
}
