package com.clubber.ClubberServer.domain.review.util;

import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.global.util.SliceUtil;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ReviewUtil {

	public static Set<String> extractKeywords(Review review) {
		return review.getReviewKeywords()
			.stream()
			.map(ReviewKeyword::getKeywordTitle)
			.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	public static Long getLastReviewId(List<Review> reviews, Pageable pageable) {
		if (SliceUtil.hasNext(reviews, pageable)) {
			return SliceUtil.getLastContent(reviews).getId();
		}
		return null;
	}
}
