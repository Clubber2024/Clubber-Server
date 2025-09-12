package com.clubber.ClubberServer.domain.review.util;

import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.domains.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.global.util.SliceUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
}
