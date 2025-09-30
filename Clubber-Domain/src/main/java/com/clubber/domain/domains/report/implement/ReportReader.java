package com.clubber.domain.domains.report.implement;

import com.clubber.domain.domains.report.domain.Report;
import com.clubber.domain.domains.report.domain.ReportStatus;
import com.clubber.domain.domains.report.exception.ReportNotFoundException;
import com.clubber.domain.domains.report.repository.ReportRepository;
import com.clubber.domain.domains.report.vo.GetOneReportResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportReader {

    private final ReportRepository reportRepository;

    public Report findById(Long id) {
        return reportRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> ReportNotFoundException.EXCEPTION);
    }

    public Page<GetOneReportResponse> findReportsPage(Pageable pageable){
       return reportRepository.findReports(pageable);
    }

    public Report findByIdAndStatus(Long id, ReportStatus status) {
        return reportRepository.findByIdAndIsDeletedFalseAndReportStatus(id, status)
            .orElseThrow(() -> ReportNotFoundException.EXCEPTION);
    }

    public List<Report> findByReviewId(Long reviewId) {
        return reportRepository.findByReviewIdAndIsDeletedFalse(reviewId);
    }

    public List<Report> findAlwaysNextReport(Long reportId, Long reviewId){
        return reportRepository.findNextReport(reportId,reviewId);
    }
}
