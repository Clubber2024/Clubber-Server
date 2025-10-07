package com.clubber.domain.review.service;

import com.clubber.common.mapper.enums.EnumMapper;
import com.clubber.common.vo.enums.EnumMapperVO;
import com.clubber.domain.club.implement.ClubReader;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.report.domain.Report;
import com.clubber.domain.domains.report.repository.ReportRepository;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewKeywordCategory;
import com.clubber.domain.domains.review.domain.ReviewSortType;
import com.clubber.domain.domains.review.implement.ReviewReader;
import com.clubber.domain.domains.review.implement.ReviewValidator;
import com.clubber.domain.domains.review.repository.ReviewKeywordRepository;
import com.clubber.domain.domains.review.repository.ReviewRepository;
import com.clubber.domain.domains.review.vo.ClubReviewResponse;
import com.clubber.domain.domains.review.vo.KeywordCountStatDto;
import com.clubber.domain.domains.review.vo.KeywordStatsVO;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.domain.review.dto.*;
import com.clubber.domain.review.mapper.ReviewMapper;
import com.clubber.domain.user.dto.GetUserReviewReportResponse;
import com.clubber.domain.user.implement.UserReader;
import com.clubber.global.common.slice.SliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewKeywordRepository reviewKeywordRepository;
    private final ReportRepository reportRepository;
    private final ReviewMapper reviewMapper;
    private final EnumMapper enumMapper;
    private final UserReader userReader;
    private final ReviewReader reviewReader;
    private final ReviewValidator reviewValidator;
    private final ClubReader clubReader;

    public List<ReviewKeywordCategoryResponse> getTotalReviewKeywords() {
        return Arrays.stream(ReviewKeywordCategory.values())
                .map(
                        (reviewKeywordCategory) -> {
                            List<EnumMapperVO> enumValues = enumMapper.toEnumValues(
                                    reviewKeywordCategory.getReviewKeywords());
                            return new ReviewKeywordCategoryResponse(reviewKeywordCategory, enumValues);
                        })
                .toList();
    }

    @Transactional
    public CreateClubReviewResponse createReview(Long clubId, CreateClubReviewRequest reviewRequest) {
        User user = userReader.getCurrentUser();
        Club club = clubReader.findById(clubId);

        club.validateAgreeToReview();
        reviewValidator.validateReviewExists(club, user);

        Review review = Review.of(user, club, reviewRequest.getContent());
        review.addKeywords(reviewRequest.getKeywords());
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.getCreateClubReviewResponse(savedReview);
    }

    @Transactional
    public void updateReviewContent(Long id, String content) {
        User user = userReader.getCurrentUser();
        Review review = reviewReader.findById(id);
        reviewValidator.validateReviewUser(user, review);
        reviewValidator.validateReviewReportExists(review);
        review.updateContent(content);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        User user = userReader.getCurrentUser();
        Review review = reviewReader.findById(reviewId);
        reviewValidator.validateReviewUser(user, review);
        review.delete();
    }

    @Transactional(readOnly = true)
    public GetClubReviewsKeywordStatsResponse getClubReviewKeywordStats(Long clubId) {
        Club club = clubReader.findById(clubId);
        club.validateAgreeToReview();

        List<KeywordCountStatDto> keywordCountStatDtoList = reviewKeywordRepository.queryReviewKeywordStatsByClubId(
                club.getId());

        final KeywordStatsVO keywordStatsVO = new KeywordStatsVO(keywordCountStatDtoList);
        return reviewMapper.getGetClubReviewsKeywordStatsResponse(club, keywordStatsVO);
    }

    //동아리 별 리뷰 조회 : Page 별 조회
    @Transactional(readOnly = true)
    public GetClubReviewsPageResponse getClubReviewsWithContent(Long clubId, Pageable pageable, ReviewSortType sortType) {
        User user = userReader.getCurrentUser();
        Club club = clubReader.findById(clubId);
        club.validateAgreeToReview();

        Page<ClubReviewResponse> clubReviewResponses = reviewRepository.queryReviewByClub(club, user, pageable, sortType);
        return reviewMapper.getGetClubReviewsPageResponse(clubReviewResponses, clubId);
    }

//    //동아리 별 리뷰 조회 : No Offset 구현
//    @Transactional(readOnly = true)
//    public GetClubReviewsSliceResponse getClubReviewsWithSliceContent(Long clubId,
//                                                                      Pageable pageable, Long reviewId) {
//        Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
//                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);
//
//        club.validateAgreeToReview();
//
//        List<Review> reviews = reviewRepository.queryReviewNoOffsetByClub(club, pageable, reviewId);
//
//        return reviewMapper.getClubReviewsSliceResponse(clubId, reviews, pageable);
//    }

    @Transactional
    public void softDeleteReviewByClubId(Long clubId) {
        reviewRepository.softDeleteReviewByClubId(clubId);
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

    @Transactional(readOnly = true)
    public SliceResponse<GetUserReviewReportResponse> getUserReviewReportResponse(Long reviewId, Long nowReviewReportId) {
        Review review = reviewReader.findById(reviewId);
        List<Report> reports = reviewRepository.queryNextReviewReport(review.getId(), nowReviewReportId);
        return reviewMapper.getUserReviewReportResponseSliceResponse(reports);
    }
}
