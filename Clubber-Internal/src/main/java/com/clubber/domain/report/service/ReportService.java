package com.clubber.domain.report.service;


import com.clubber.common.mapper.enums.EnumMapper;
import com.clubber.common.vo.enums.EnumMapperVO;
import com.clubber.domain.domains.report.domain.Report;
import com.clubber.domain.domains.report.domain.ReportStatus;
import com.clubber.domain.domains.report.implement.ReportReader;
import com.clubber.domain.domains.report.implement.ReportValidator;
import com.clubber.domain.domains.report.vo.GetOneReportResponse;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.implement.ReviewReader;
import com.clubber.domain.domains.review.implement.ReviewValidator;
import com.clubber.domain.report.dto.GetNextAlwaysReportRequest;
import com.clubber.domain.report.dto.GetOneReportDetailResponse;
import com.clubber.domain.report.dto.PutReportRequest;
import com.clubber.global.common.page.PageResponse;
import com.clubber.global.common.slice.SliceResponse;
import com.clubber.global.util.SliceUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportReader reportReader;
    private final ReviewReader reviewReader;
    private final ReviewValidator reviewValidator;
    private final ReportValidator reportValidator;
    private final EnumMapper enumMapper;

    @Transactional(readOnly = true)
    public PageResponse<GetOneReportResponse> getReviewReports(Pageable pageable) {
        Page<GetOneReportResponse> reports = reportReader.findReportsPage(pageable);
        return PageResponse.of(reports);
    }

    @Transactional(readOnly = true)
    public SliceResponse<GetOneReportDetailResponse> getNextReport(
        GetNextAlwaysReportRequest request) {
        List<Report> alwaysNextReports = reportReader.findAlwaysNextReport(request.nowReportId(),
            request.reviewId());
        List<GetOneReportDetailResponse> reportResponses = alwaysNextReports.stream()
            .map(GetOneReportDetailResponse::from)
            .toList();

        return SliceUtil.valueOf(reportResponses, Pageable.ofSize(1));
    }

    @Transactional
    public void postReportStatus(PutReportRequest request) {
        Review review = reviewReader.findById(request.reviewId());
        reviewValidator.validateReviewStatus(review);
        reportValidator.hasPendingReport(review);

        if (request.reportStatus() == ReportStatus.APPROVED) {
            review.hide();
        }

        reportReader.findByReviewId(review.getId())
            .forEach(report -> report.changeStatus(request.reportStatus()));
    }

    public List<EnumMapperVO> getReportStatus() {
        return enumMapper.get("ReportStatus");
    }

}
