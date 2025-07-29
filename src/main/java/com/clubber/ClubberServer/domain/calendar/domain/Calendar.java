package com.clubber.ClubberServer.domain.calendar.domain;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    private RecruitType recruitType;

    private String url;

    @Column(columnDefinition = "DATETIME(0)")
    private LocalDateTime startAt;

    @Column(columnDefinition = "DATETIME(0)")
    private LocalDateTime endAt;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    private AccountRole writerRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private boolean isDeleted = false;

    @Builder
    public Calendar(Long id, String title, RecruitType recruitType, String url,
                    LocalDateTime startAt, LocalDateTime endAt, AccountRole writerRole, Club club,
                    boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.recruitType = recruitType;
        this.url = url;
        this.startAt = startAt;
        this.endAt = endAt;
        this.writerRole = writerRole;
        this.isDeleted = isDeleted;
        this.club = club;
    }

    public void delete() {
        isDeleted = true;
    }

    public void update(String title, RecruitType recruitType, LocalDateTime startAt,
                       LocalDateTime endAt, String url) {
        this.title = title;
        this.recruitType = recruitType;
        this.startAt = startAt;
        this.endAt = endAt;
        this.url = url;
    }

    public void update(String title, RecruitType recruitType, LocalDateTime startAt,
                       LocalDateTime endAt) {
        this.title = title;
        this.recruitType = recruitType;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public String getStatus() {
        CalendarStatus status = CalendarStatus.getStatus(LocalDateTime.now(), startAt, endAt, recruitType);
        return status.getTitle();
    }
}
