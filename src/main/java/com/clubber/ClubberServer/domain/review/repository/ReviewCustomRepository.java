package com.clubber.ClubberServer.domain.review.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.user.domain.User;

public interface ReviewCustomRepository {

	List<Review> queryReviewByUserOrderByIdDesc(User user);

	Page<Review> queryReviewByClub(Club club, Pageable pageable);
}
