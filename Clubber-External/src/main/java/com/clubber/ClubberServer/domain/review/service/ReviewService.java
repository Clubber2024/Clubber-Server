package com.clubber.ClubberServer.domain.review.service;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.club.exception.ClubNotFoundException;
import com.clubber.domain.domains.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeywordCategory;
import com.clubber.ClubberServer.domain.review.domain.VerifiedStatus;
import com.clubber.ClubberServer.domain.review.dto.*;
import com.clubber.domain.domains.review.exception.UserAlreadyReviewedException;
import com.clubber.ClubberServer.domain.review.mapper.ReviewMapper;
import com.clubber.ClubberServer.domain.review.repository.ReviewKeywordRepository;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.review.vo.KeywordStatsVO;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.ClubberServer.domain.user.implement.UserReader;
import com.clubber.ClubberServer.global.event.review.approve.ReviewApproveEvnetPublisher;
import com.clubber.common.mapper.enums.EnumMapper;
import com.clubber.common.vo.enums.EnumMapperVO;
import jakarta.validation.Valid;
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
    private final ReviewMapper reviewMapper;
    private final ClubRepository clubRepository;
    private final EnumMapper enumMapper;
    private final ReviewApproveEvnetPublisher publisher;
    private final UserReader userReader;

    public List<ReviewKeywordCategoryResponse> getTotalReviewKeywords() {
        return Arrays.stream(ReviewKeywordCategory.values())
                .map(
                        (reviewKeywordCategory) -> {
                            List<EnumMapperVO> enumValues = enumMapper.toEnumValues(reviewKeywordCategory.getReviewKeywords());
                            return new ReviewKeywordCategoryResponse(reviewKeywordCategory, enumValues);
                        })
                .toList();
    }

    @Transactional
    public CreateClubReviewResponse createReview(Long clubId,
                                                 @Valid CreateClubReviewRequest reviewRequest) {
        User user = userReader.getCurrentUser();
        Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        club.validateAgreeToReview();
        validateReviewExists(club, user);

        Review review = Review.of(user, club, reviewRequest.getContent(),
                reviewRequest.getAuthImage());
        review.addKeywords(reviewRequest.getKeywords());
        Review savedReview = reviewRepository.save(review);

        publisher.throwReviewApproveEvent(savedReview);
        return reviewMapper.getCreateClubReviewResponse(savedReview);
    }

    private void validateReviewExists(Club club, User user) {
        if (reviewRepository.existsByClubAndUserAndNotApprovedStatusDeleted(club, user)) {
            throw UserAlreadyReviewedException.EXCEPTION;
        }
    }

    @Transactional(readOnly = true)
    public GetClubReviewAgreedStatusResponse getClubReviewAgreedStatus(Long clubId) {
        Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        return GetClubReviewAgreedStatusResponse.from(club);
    }

    @Transactional(readOnly = true)
    public GetClubReviewsKeywordStatsResponse getClubReviewKeywordStats(Long clubId) {
        Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        club.validateAgreeToReview();

        List<KeywordCountStatDto> keywordCountStatDtoList = reviewKeywordRepository.queryReviewKeywordStatsByClubId(
                club.getId());

        final KeywordStatsVO keywordStatsVO = new KeywordStatsVO(keywordCountStatDtoList);
        return reviewMapper.getGetClubReviewsKeywordStatsResponse(club, keywordStatsVO);
    }

    //동아리 별 리뷰 조회 : Page 별 조회
    @Transactional(readOnly = true)
    public GetClubReviewsPageResponse getClubReviewsWithContent(Long clubId,
                                                                Pageable pageable, VerifiedStatus verifiedStatus) {
        Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        club.validateAgreeToReview();

        Page<Review> reviews = reviewRepository.queryReviewByClub(club, pageable, null,
                verifiedStatus);
        return reviewMapper.getGetClubReviewsPageResponse(reviews, clubId);
    }

    //동아리 별 리뷰 조회 : No Offset 구현
    @Transactional(readOnly = true)
    public GetClubReviewsSliceResponse getClubReviewsWithSliceContent(Long clubId,
                                                                      Pageable pageable, Long reviewId) {
        Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);

        club.validateAgreeToReview();

        List<Review> reviews = reviewRepository.queryReviewNoOffsetByClub(club, pageable, reviewId,
                null);

        return reviewMapper.getClubReviewsSliceResponse(clubId, reviews, pageable);
    }

    public List<EnumMapperVO> getTotalKeywords() {
        return enumMapper.get("Keyword");
    }

    @Transactional
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    @Transactional
    public void softDeleteReviewByClubId(Long clubId) {
        reviewRepository.softDeleteReviewByClubId(clubId);
    }
}
