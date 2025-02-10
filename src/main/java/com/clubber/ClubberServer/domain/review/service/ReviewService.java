package com.clubber.ClubberServer.domain.review.service;

import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.APPROVED;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.VerifiedStatus;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.CreateClubReviewRequest;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewAgreedStatusResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsPageResponse;
import com.clubber.ClubberServer.domain.review.dto.GetClubReviewsSliceResponse;
import com.clubber.ClubberServer.domain.review.dto.KeywordCountStatDto;
import com.clubber.ClubberServer.domain.review.vo.KeywordStatsVO;
import com.clubber.ClubberServer.domain.review.exception.UserAlreadyReviewedException;
import com.clubber.ClubberServer.domain.review.mapper.ReviewMapper;
import com.clubber.ClubberServer.domain.review.repository.ReviewKeywordRepository;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
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
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ReviewKeywordRepository reviewKeywordRepository;
	private final ReviewMapper reviewMapper;
	private final ClubRepository clubRepository;
	private final EnumMapper enumMapper;
	private final ReviewApproveEvnetPublisher publisher;
	private final UserReadService userReadService;

	@Transactional
	public CreateClubReviewResponse createReview(Long clubId,
		@Valid CreateClubReviewRequest reviewRequest) {
		User user = userReadService.getUser();
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

		KeywordStatsVO keywordStatsVO = new KeywordStatsVO();
		keywordStatsVO.updateKeywordStat(keywordCountStatDtoList);
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
}
