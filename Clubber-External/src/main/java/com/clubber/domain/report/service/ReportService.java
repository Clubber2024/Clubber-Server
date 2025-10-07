package com.clubber.domain.report.service;

import com.clubber.common.mapper.enums.EnumMapper;
import com.clubber.common.vo.enums.EnumMapperVO;
import java.util.List;

import com.clubber.domain.domains.report.domain.Report;
import com.clubber.domain.domains.report.repository.ReportRepository;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.implement.ReviewReader;
import com.clubber.domain.domains.review.implement.ReviewValidator;
import com.clubber.domain.domains.review.repository.ReviewRepository;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.domain.review.dto.CreateReviewReportRequest;
import com.clubber.domain.review.dto.CreateReviewReportResponse;
import com.clubber.domain.user.implement.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final EnumMapper enumMapper;
    private final ReviewValidator reviewValidator;
    private final ReviewReader reviewReader;
    private final ReportRepository reportRepository;
    private final UserReader userReader;

    public List<EnumMapperVO> getReportReasons() {
        return enumMapper.get("ReportReason");
    }

    @Transactional
    public CreateReviewReportResponse createReviewReport(Long reviewId, CreateReviewReportRequest request) {
        User user = userReader.getCurrentUser();
        Review review = reviewReader.findById(reviewId);
        reviewValidator.validateNotSelfReview(user, review);
        reviewValidator.validateReviewStatus(review);
        reviewValidator.validateReportReason(request.getReportReason(), request.getDetailReason());

        Report report = Report.of(review, request.getReportReason(),
                request.getDetailReason());
        Report savedReport = reportRepository.save(report);

        return CreateReviewReportResponse.of(review, savedReport);
    }

}
