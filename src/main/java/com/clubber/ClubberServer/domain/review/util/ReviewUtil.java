package com.clubber.ClubberServer.domain.review.util;

import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.NULL_CONTENT;
import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.PENDING;

import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.global.util.SliceUtil;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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

	public static String checkBlankContent(String content) {
		if (hasContent(content)) {
			return content;
		}
		return null;
	}

	public static boolean hasContent(String content) {
		if (StringUtils.hasText(content)) {
			return true;
		}
		return false;
	}

	public static ApprovedStatus checkBlankContentApprovedStatus(String content) {
		if (hasContent(content)) {
			return PENDING;
		}
		return NULL_CONTENT;
	}
}
