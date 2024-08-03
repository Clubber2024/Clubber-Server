package com.clubber.ClubberServer.domain.review.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.*;
import com.clubber.ClubberServer.domain.review.exception.UserAlreadyReviewedException;
import com.clubber.ClubberServer.domain.review.repository.ReviewKeywordRepository;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;

import com.clubber.ClubberServer.global.enummapper.EnumMapper;
import com.clubber.ClubberServer.global.enummapper.EnumMapperVO;
import java.util.*;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public CreateReviewClubWithContentResponse createReviewsByContent(Long clubId, CreateReviewClubWithContentRequest reviewRequest){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);
        if(reviewRepository.existsByUserAndClub(user, club)){
            throw UserAlreadyReviewedException.EXCEPTION;
        }
        Review review = Review.of(user, club, reviewRequest.getContent());

        return createReviewKeyword(reviewRequest, reviewRepository.save(review));
    }

    private CreateReviewClubWithContentResponse createReviewKeyword(CreateReviewClubWithContentRequest reviewRequest, Review review){
        List<ReviewKeyword> reviewKeywords = reviewRequest.toEntity(review);
        List<ReviewKeyword> savedKeywords = reviewKeywordRepository.saveAll(reviewKeywords);
        return CreateReviewClubWithContentResponse.of(review, savedKeywords);
    }

    @Transactional(readOnly = true)
    public ClubReviewResponse getClubReviews(Long clubId){
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);
        List<ReviewKeyword> reviewKeywords = reviewKeywordRepository.queryReviewKeywordByClubId(club.getId());
        return ClubReviewResponse.of(club, reviewKeywords);
    }

    @Transactional(readOnly = true)
    public ClubReviewKeywordStatsResponse getClubReviewKeywordStats(Long clubId){
        Club club = clubRepository.findById(clubId)
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
    public ClubReviewsWithContentResponse getClubReviewsWithContent(Long clubId){
        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);
        List<Review> reviews = reviewRepository.queryReviewByClub(club);
        return ClubReviewsWithContentResponse.of(reviews, club.getId());
    }

    public List<EnumMapperVO> getTotalKeywords() {
        return enumMapper.get("Keyword");
    }
}
