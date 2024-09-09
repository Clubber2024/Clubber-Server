package com.clubber.ClubberServer.domain.review.service;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.clubber.ClubberServer.domain.club.exception.ClubNotAgreeToProvideReviewException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsWithPageContentResponse;
import com.clubber.ClubberServer.domain.review.dto.CreateReviewClubWithContentRequest;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewsWithContentResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsWithSliceContentResponse;
import com.clubber.ClubberServer.domain.review.dto.KeywordStats;
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

		return CreateClubReviewsWithContentResponse.of(savedReview, savedReview.getReviewKeywords());
	}

	@Transactional(readOnly = true)
	public GetClubReviewsKeywordStatsResponse getClubReviewKeywordStats(Long clubId) {
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		club.validateAgreeToReview();

		List<KeywordStats> keywordStats = reviewKeywordRepository.queryReviewKeywordStatsByClubId(
			club.getId());

		Map<Keyword, Long> keywordMap = new EnumMap<>(Keyword.class);
		keywordStats.stream()
			.forEach(stats -> keywordMap.put(stats.getKeyword(), stats.getCount()));
		Arrays.stream(Keyword.values())
			.forEach(keyword -> keywordMap.putIfAbsent(keyword, 0L));

		return GetClubReviewsKeywordStatsResponse.of(club, keywordMap);
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

		List<Review> reviews = reviewRepository.queryReviewNoOffsetByClub(club, pageable, reviewId);

		return GetClubReviewsWithSliceContentResponse.of(reviews, clubId, pageable);
	}

	public List<EnumMapperVO> getTotalKeywords() {
		return enumMapper.get("Keyword");
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
