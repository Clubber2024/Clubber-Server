package com.clubber.ClubberServer.domain.review.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.ClubReviewResponse;
import com.clubber.ClubberServer.domain.review.dto.ReviewCreateResponse;
import com.clubber.ClubberServer.domain.review.dto.ReviewRequest;
import com.clubber.ClubberServer.domain.review.repository.ReviewKeywordRepository;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        Club club = clubRepository.findById(clubId).get();
        Review review = Review.of(user, club);

        return createReviewKeyword(reviewRequest, reviewRepository.save(review));
    }

    public ReviewCreateResponse createReviewKeyword(ReviewRequest reviewRequest, Review review){
        List<Keyword> keywords = reviewRequest.getKeywords();
        List<ReviewKeyword> reviewKeywords = keywords.stream()
                .map((keyword -> ReviewKeyword.of(review, keyword)))
                .collect(Collectors.toList());

        List<ReviewKeyword> savedKeywords = reviewKeywordRepository.saveAll(reviewKeywords);
        return ReviewCreateResponse.of(review, savedKeywords);
    }

    public ClubReviewResponse getClubReviews(Long clubId){
        Club club = clubRepository.findById(clubId).get();
        List<ReviewKeyword> reviewKeywords = reviewKeywordRepository.queryReviewKeywordByClubId(club.getId());
        return ClubReviewResponse.of(club, reviewKeywords);
    }
}
