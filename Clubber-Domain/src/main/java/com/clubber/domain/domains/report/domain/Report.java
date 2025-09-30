package com.clubber.domain.domains.report.domain;

import com.clubber.domain.common.BaseEntity;
import com.clubber.domain.domains.report.exception.ReviewAlreadyHiddenException;
import com.clubber.domain.domains.review.domain.Review;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    @NotNull
    private Review review;

    @NotNull
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    private ReportReason reportReason;

    private String detailReason;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @NotNull
    @Builder.Default
    private ReportStatus reportStatus = ReportStatus.PENDING;

    @Builder.Default
    private boolean isDeleted = false;

    public static Report of(Review review, ReportReason reportReason, String detailReason) {
        return Report.builder()
            .review(review)
            .reportReason(reportReason)
            .detailReason(detailReason)
            .build();
    }

    public void changeStatus(ReportStatus status) {
        if (reportStatus != ReportStatus.PENDING) {
            throw ReviewAlreadyHiddenException.EXCEPTION;
        }
        reportStatus = status;
    }

}
