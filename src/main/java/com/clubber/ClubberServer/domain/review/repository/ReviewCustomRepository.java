package com.clubber.ClubberServer.domain.review.repository;

import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.user.domain.User;
import java.util.List;



public interface ReviewCustomRepository {

    List<Review> queryReviewByUserOrderByIdDesc(User user);
}
