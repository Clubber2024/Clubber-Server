package com.clubber.domain.domains.review.repository;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewSortType;
import com.clubber.domain.domains.review.vo.ClubReviewResponse;
import com.clubber.domain.domains.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewCustomRepository {

    List<Review> queryReviewByUserOrderByIdDesc(User user);

    Page<ClubReviewResponse> queryReviewByClub(Club club, Pageable pageable, ReviewSortType sortType);

    List<Review> queryReviewNoOffsetByClub(Club club, Pageable pageable, Long reviewId);

    boolean existsByClubAndUser(Club club, User user);

    void softDeleteReviewByClubId(Long clubId);
}
