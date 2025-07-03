package com.clubber.ClubberServer.domain.calender.entity;

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
public class Calender {
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

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @NotNull
    private AccountRole writerRole;

    private boolean isDeleted = false;

    @Builder
    public Calender(Long id, String title, RecruitType recruitType, String url, LocalDateTime startAt, LocalDateTime endAt, AccountRole writerRole, boolean isDeleted) {
        this.id = id;
        this.title = title;
        this.recruitType = recruitType;
        this.url = url;
        this.startAt = startAt;
        this.endAt = endAt;
        this.writerRole = writerRole;
        this.isDeleted = isDeleted;
    }

    public void delete() {
        isDeleted = true;
    }

    public void update(String title, RecruitType recruitType, LocalDateTime startAt, LocalDateTime endAt, String url) {
        this.title = title;
        this.recruitType = recruitType;
        this.startAt = startAt;
        this.endAt = endAt;
        this.url = url;
    }

    public String getStatus() {
        CalenderStatus status = CalenderStatus.getStatus(startAt, endAt, recruitType);
        return status.getTitle();
    }
}
