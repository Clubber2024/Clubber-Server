package com.clubber.ClubberServer.domain.review.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.dto.response.CreateClubReviewsWithContentResponse;
import com.clubber.ClubberServer.domain.review.dto.request.CreateReviewClubWithContentRequest;
import com.clubber.ClubberServer.domain.review.dto.response.GetClubReviewAgreedStatusResponse;
import com.clubber.ClubberServer.domain.review.dto.response.GetClubReviewsKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.response.GetClubReviewsWithPageContentResponse;
import com.clubber.ClubberServer.domain.review.dto.response.GetClubReviewsWithSliceContentResponse;
import com.clubber.ClubberServer.domain.review.dto.response.KeywordStat;
import com.clubber.ClubberServer.domain.review.dto.response.KeywordStats;
import com.clubber.ClubberServer.domain.review.exception.UserAlreadyReviewedException;
import com.clubber.ClubberServer.domain.review.repository.ReviewKeywordRepository;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.domain.user.service.UserReadService;
import com.clubber.ClubberServer.global.event.review.approve.ReviewApproveEvnetPublisher;
import com.clubber.ClubberServer.global.mapper.enums.EnumMapper;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ReviewKeywordRepository reviewKeywordRepository;
	private final UserRepository userRepository;
	private final ClubRepository clubRepository;
	private final EnumMapper enumMapper;
	private final ReviewApproveEvnetPublisher publisher;
	private final UserReadService userReadService;

	@Transactional
	public CreateClubReviewsWithContentResponse createReviewsByContent(Long clubId,
		@Valid CreateReviewClubWithContentRequest reviewRequest) {
		User user = userReadService.getUser();

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
		return CreateClubReviewsWithContentResponse.of(savedReview,
			savedReview.getReviewKeywords());
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

		List<KeywordStat> keywordStatList = reviewKeywordRepository.queryReviewKeywordStatsByClubId(
			club.getId());

		KeywordStats keywordStats = new KeywordStats();
		keywordStats.updateKeywordStat(keywordStatList);
		return GetClubReviewsKeywordStatsResponse.of(club, keywordStats);
	}

	//동아리 별 리뷰 조회 : Page 별 조회 
	@Transactional(readOnly = true)
	public GetClubReviewsWithPageContentResponse getClubReviewsWithContent(Long clubId,
		Pageable pageable) {
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		club.validateAgreeToReview();

		Page<Review> reviews = reviewRepository.queryReviewByClub(club, pageable);
		return GetClubReviewsWithPageContentResponse.of(reviews, club.getId());
	}

	//동아리 별 리뷰 조회 : No Offset 구현 
	@Transactional(readOnly = true)
	public GetClubReviewsWithSliceContentResponse getClubReviewsWithSliceContent(Long clubId,
		Pageable pageable, Long reviewId) {
		Club club = clubRepository.findClubByIdAndIsDeleted(clubId, false)
			.orElseThrow(() -> ClubNotFoundException.EXCEPTION);

		club.validateAgreeToReview();

		List<Review> reviews = reviewRepository.queryReviewNoOffsetByClub(club, pageable, reviewId,
			null);

		return GetClubReviewsWithSliceContentResponse.of(reviews, clubId, pageable);
	}

	public List<EnumMapperVO> getTotalKeywords() {
		return enumMapper.get("Keyword");
	}

	@Transactional
	public void saveReview(Review review) {
		reviewRepository.save(review);
	}
}
