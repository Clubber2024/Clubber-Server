package com.clubber.domain.internal.domain.report;

import com.clubber.domain.common.BaseEntity;
import com.clubber.domain.domains.review.domain.ReportStatus;
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
public class ReviewReport extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    @NotNull
    private Review review;

    @NotNull
    private ReportReason reportReason;

    private String detailReason;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Enumerated(EnumType.STRING)
    @NotNull
    @Builder.Default
    private ReportStatus reportStatus = ReportStatus.VISIBLE;

    public static ReviewReport of(Review review, ReportReason reportReason, String detailReason) {
        return ReviewReport.builder()
            .review(review)
            .reportReason(reportReason)
            .detailReason(detailReason)
            .build();
    }

    public void hide(){
//        if (reportStatus==ReportStatus.HIDDEN){
//            throw
//
//        }
        reportStatus = ReportStatus.HIDDEN;
    }

}
