package com.clubber.ClubberServer.domain.review.service;

import java.util.List;

import com.clubber.ClubberServer.domain.review.dto.*;
import com.clubber.ClubberServer.global.event.review.approve.ReviewApproveEvnetPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.exception.UserAlreadyReviewedException;
import com.clubber.ClubberServer.domain.review.repository.ReviewKeywordRepository;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.global.enummapper.EnumMapper;
import com.clubber.ClubberServer.global.enummapper.EnumMapperVO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ReviewKeywordRepository reviewKeywordRepository;
	private final UserRepository userRepository;
	private final ClubRepository clubRepository;
	private final EnumMapper enumMapper;
	private final ReviewApproveEvnetPublisher publisher;

	@Transactional
	public CreateClubReviewsWithContentResponse createReviewsByContent(Long clubId,
		@Valid CreateReviewClubWithContentRequest reviewRequest) {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		User user = userRepository.findById(currentUserId)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);

		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		club.validateAgreeToReview();

		if (reviewRepository.existsByUserAndClub(user, club)) {
			throw UserAlreadyReviewedException.EXCEPTION;
		}

		Review review = reviewRequest.toReviewEntity(user, club);
		reviewRequest.toReviewKeywordEntities(review);

		Review savedReview = reviewRepository.save(review);

		publisher.throwReviewApproveEvent(savedReview);
		return CreateClubReviewsWithContentResponse.of(savedReview, savedReview.getReviewKeywords());
	}

	@Transactional(readOnly = true)
	public GetClubReviewAgreedStatusResponse getClubReviewAgreedStatus(Long clubId){
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		return GetClubReviewAgreedStatusResponse.from(club);
	}

	@Transactional(readOnly = true)
	public GetClubReviewsKeywordStatsResponse getClubReviewKeywordStats(Long clubId) {
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		club.validateAgreeToReview();

		List<KeywordStat> keywordStatList = reviewKeywordRepository.queryReviewKeywordStatsByClubId(
			club.getId());

		KeywordStats keywordStats = new KeywordStats();
		keywordStats.updateKeywordStat(keywordStatList);
		return GetClubReviewsKeywordStatsResponse.of(club, keywordStats);
	}

	//동아리 별 리뷰 조회 : Page 별 조회 
	@Transactional(readOnly = true)
	public GetClubReviewsWithPageContentResponse getClubReviewsWithContent(Long clubId, Pageable pageable) {
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		club.validateAgreeToReview();

		Page<Review> reviews = reviewRepository.queryReviewByClub(club, pageable);
		return GetClubReviewsWithPageContentResponse.of(reviews, club.getId());
	}

	//동아리 별 리뷰 조회 : No Offset 구현 
	@Transactional(readOnly = true)
	public GetClubReviewsWithSliceContentResponse getClubReviewsWithSliceContent(Long clubId, Pageable pageable, Long reviewId){
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		club.validateAgreeToReview();

		List<Review> reviews = reviewRepository.queryReviewNoOffsetByClub(club, pageable, reviewId, null);

		return GetClubReviewsWithSliceContentResponse.of(reviews, clubId, pageable);
	}

	public List<EnumMapperVO> getTotalKeywords() {
		return enumMapper.get("Keyword");
	}

	@Transactional
	public void saveReview(Review review) {
		reviewRepository.save(review);
	}

	/**
	 * 양방향 테스트 용도 메서드 
	 */
	@Deprecated
	@Transactional(readOnly = true)
	public ClubReviewResponse getClubReviews(Long clubId) {
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);
		List<ReviewKeyword> reviewKeywords = reviewKeywordRepository.queryReviewKeywordByClubId(club.getId());
		return ClubReviewResponse.of(club, reviewKeywords);
	}
}
