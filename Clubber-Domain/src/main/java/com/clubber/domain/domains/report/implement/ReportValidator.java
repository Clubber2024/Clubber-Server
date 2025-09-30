package com.clubber.domain.domains.report.implement;

import com.clubber.domain.domains.report.domain.ReportStatus;
import com.clubber.domain.domains.report.exception.ReportNotFoundException;
import com.clubber.domain.domains.report.repository.ReportRepository;
import com.clubber.domain.domains.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportValidator {
    private final ReportRepository reportRepository;

    public void hasPendingReport(Review review) {
        if (!reportRepository.existsByReviewAndReportStatusAndIsDeletedFalse(review,
            ReportStatus.PENDING)){
            throw ReportNotFoundException.EXCEPTION;
        }
    }

}
