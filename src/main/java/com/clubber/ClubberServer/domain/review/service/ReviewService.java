package com.clubber.ClubberServer.domain.review.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewKeywordStatsResponse;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.KeywordStats;
import com.clubber.ClubberServer.domain.review.dto.ReviewCreateResponse;
import com.clubber.ClubberServer.domain.review.dto.ReviewRequest;
import com.clubber.ClubberServer.domain.review.exception.UserAlreadyReviewedException;
import com.clubber.ClubberServer.domain.review.repository.ReviewKeywordRepository;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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

    @Transactional
    public ReviewCreateResponse createReview(Long clubId, ReviewRequest reviewRequest){
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = userRepository.findById(currentUserId)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> ClubNotFoundException.EXCEPTION);
        if(reviewRepository.existsByUserAndClub(user, club)){
            throw UserAlreadyReviewedException.EXCEPTION;
        }
        Review review = Review.of(user, club);

        return createReviewKeyword(reviewRequest, reviewRepository.save(review));
    }

    private ReviewCreateResponse createReviewKeyword(ReviewRequest reviewRequest, Review review){
        Set<Keyword> keywords = reviewRequest.getKeywords();
        List<ReviewKeyword> reviewKeywords = keywords.stream()
                .map((keyword -> ReviewKeyword.of(review, keyword)))
                .collect(Collectors.toList());

        List<ReviewKeyword> savedKeywords = reviewKeywordRepository.saveAll(reviewKeywords);
        return ReviewCreateResponse.of(review, savedKeywords);
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
}
