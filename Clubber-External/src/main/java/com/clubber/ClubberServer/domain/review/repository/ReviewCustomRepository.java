package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.VerifiedStatus;
import com.clubber.ClubberServer.domain.user.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewCustomRepository {

	List<Review> queryReviewByUserOrderByIdDesc(User user);


	Page<Review> queryReviewByClub(Club club, Pageable pageable, ApprovedStatus approvedStatus, VerifiedStatus verifiedStatus);

	List<Review> queryReviewNoOffsetByClub(Club club, Pageable pageable, Long reviewId,
		ApprovedStatus approvedStatus);

	boolean existsByClubAndUserAndNotApprovedStatusDeleted(Club club, User user);

	Optional<Review> findByIdAndNotDeletedApprovedStatus(Long reviewId);

	void softDeleteReviewByClubId(Long clubId);
}
