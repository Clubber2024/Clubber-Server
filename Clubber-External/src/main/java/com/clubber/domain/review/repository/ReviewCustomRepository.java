package com.clubber.domain.review.repository;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.domain.ApprovedStatus;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.VerifiedStatus;
import com.clubber.domain.domains.user.domain.User;
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
