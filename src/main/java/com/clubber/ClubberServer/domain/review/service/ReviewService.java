package com.clubber.ClubberServer.domain.review.service;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewsWithContentDetailResponse;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewsWithContentResponse;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewsWithSliceContentResponse;
import com.clubber.ClubberServer.domain.review.dto.CreateReviewClubWithContentRequest;
import com.clubber.ClubberServer.domain.review.dto.CreateReviewClubWithContentResponse;
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
import com.clubber.ClubberServer.global.page.SliceResponse;
import com.clubber.ClubberServer.global.page.SliceUtil;

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
	public CreateReviewClubWithContentResponse createReviewsByContent(Long clubId,
		CreateReviewClubWithContentRequest reviewRequest) {
		Long currentUserId = SecurityUtils.getCurrentUserId();
		User user = userRepository.findById(currentUserId)
			.orElseThrow(() -> UserNotFoundException.EXCEPTION);

		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);
		if (reviewRepository.existsByUserAndClub(user, club)) {
			throw UserAlreadyReviewedException.EXCEPTION;
		}

		Review review = Review.of(user, club, reviewRequest.getContent());
		reviewRequest.getKeywords().stream().forEach(review::setReviewKeywords);

		Review savedReview = reviewRepository.save(review);

		return CreateReviewClubWithContentResponse.of(savedReview, savedReview.getReviewKeywords());
	}

	@Transactional(readOnly = true)
	public ClubReviewResponse getClubReviews(Long clubId) {
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);
		List<ReviewKeyword> reviewKeywords = reviewKeywordRepository.queryReviewKeywordByClubId(club.getId());
		return ClubReviewResponse.of(club, reviewKeywords);
	}

	@Transactional(readOnly = true)
	public ClubReviewKeywordStatsResponse getClubReviewKeywordStats(Long clubId) {
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);
		List<KeywordStats> keywordStats = reviewKeywordRepository.queryReviewKeywordStatsByClubId(
			club.getId());

		Map<Keyword, Long> keywordMap = new EnumMap<>(Keyword.class);
		keywordStats.stream()
			.forEach(stats -> keywordMap.put(stats.getKeyword(), stats.getCount()));
		Arrays.stream(Keyword.values())
			.forEach(keyword -> keywordMap.putIfAbsent(keyword, 0L));

		return ClubReviewKeywordStatsResponse.of(club, keywordMap);
	}

	@Transactional(readOnly = true)
	public ClubReviewsWithContentResponse getClubReviewsWithContent(Long clubId, Pageable pageable) {
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);
		Page<Review> reviews = reviewRepository.queryReviewByClub(club, pageable, ApprovedStatus.APPROVED);
		return ClubReviewsWithContentResponse.of(reviews, club.getId());
	}

	public ClubReviewsWithSliceContentResponse getClubReviewsWithSliceContent(Long clubId, Pageable pageable, Long reviewId){
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);
		List<Review> reviews = reviewRepository.queryReviewNoOffsetByClub(club, pageable, reviewId);

		List<ClubReviewsWithContentDetailResponse> reviewDetails = reviews.stream()
			.map(ClubReviewsWithContentDetailResponse::of)
			.collect(Collectors.toList());
		
	}

	public List<EnumMapperVO> getTotalKeywords() {
		return enumMapper.get("Keyword");
	}
}
