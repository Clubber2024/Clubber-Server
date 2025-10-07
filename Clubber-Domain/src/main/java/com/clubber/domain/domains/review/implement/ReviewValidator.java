package com.clubber.domain.domains.review.implement;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.report.domain.ReportReason;
import com.clubber.domain.domains.report.exception.ReviewDetailReasonRequiredException;
import com.clubber.domain.domains.report.repository.ReportRepository;
import com.clubber.domain.domains.review.domain.ReportStatus;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.exception.*;
import com.clubber.domain.domains.review.repository.ReviewLikeRepository;
import com.clubber.domain.domains.review.repository.ReviewRepository;
import com.clubber.domain.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;
    private final ReportRepository reportRepository;

    public void validateReviewUser(User user, Review review) {
        if (!review.getUser().equals(user)) {
            throw ReviewUserNotMatchException.EXCEPTION;
        }
    }

    public void validateReviewClub(Club club, Review review) {
        if (!review.getClub().equals(club)) {
            throw ReviewClubNotMatchException.EXCEPTION;
        }
    }

    public void validateReviewExists(User user, Review review) {
        boolean isExists = reviewLikeRepository.existsByReviewAndUserAndIsDeletedFalse(review,
            user);
        if (isExists) {
            throw ReviewAlreadyLikedException.EXCEPTION;
        }
    }

    public void validateReviewExists(Club club, User user) {
        if (reviewRepository.existsByClubAndUser(club, user)) {
            throw UserAlreadyReviewedException.EXCEPTION;
        }
    }

    public void validateReviewReportExists(Review review) {
        if (reportRepository.existsByReviewAndIsDeletedFalse(review)){
            throw ReviewHasReportException.EXCEPTION;
        }
    }

    public void validateNotSelfReview(User user, Review review) {
        if (review.getUser().equals(user)) {
            throw ReviewSelfReportNotAllowedException.EXCEPTION;
        }
    }
    public void validateReportReason(ReportReason reportReason, String detailReason){
        if (reportReason == ReportReason.OTHER && detailReason == null){
            throw ReviewDetailReasonRequiredException.EXCEPTION;
        }
    }

    public void validateReviewStatus(Review review){
        if (review.getReportStatus()== ReportStatus.HIDDEN){
            throw ReviewAlreadyHiddenException.EXCEPTION;
        }
    }
}
