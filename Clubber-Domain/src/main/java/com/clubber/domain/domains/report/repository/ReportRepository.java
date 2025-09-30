package com.clubber.domain.domains.report.repository;

import com.clubber.domain.domains.report.domain.Report;
import com.clubber.domain.domains.report.domain.ReportStatus;
import com.clubber.domain.domains.review.domain.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long>, ReportCustomRepository {

    Optional<Report> findByIdAndIsDeletedFalse(Long id);
    Optional<Report> findByIdAndIsDeletedFalseAndReportStatus(Long id, ReportStatus reportStatus);
    List<Report> findByReviewIdAndIsDeletedFalse(Long reviewId);
    boolean existsByReviewAndReportStatusAndIsDeletedFalse(Review review, ReportStatus reportStatus);

}
