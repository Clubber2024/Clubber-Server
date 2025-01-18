package com.clubber.ClubberServer.domain.review.mapper;

import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.review.dto.response.ClubReviewsWithContentDetailResponse;
import com.clubber.ClubberServer.domain.review.dto.response.CreateClubReviewsWithContentResponse;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

	public CreateClubReviewsWithContentResponse getCreateClubReviewsWithContentResponse(Review review) {
		Set<String> keywords = getKeywords(review.getReviewKeywords());
		return CreateClubReviewsWithContentResponse.of(review, keywords);
	}

	private static Set<String> getKeywords(List<ReviewKeyword> reviewKeywords){
		return reviewKeywords.stream()
			.map(ReviewKeyword::getKeywordTitle)
			.collect(Collectors.toCollection(LinkedHashSet::new));
	}
}
