package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.global.common.slice.SliceResponse;
import com.clubber.ClubberServer.global.util.SliceUtil;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAdminPendingReviewsSliceResponse {

	private final Long lastReviewId; 

	private final SliceResponse<GetAdminsPendingReviews> reviews;

	public static GetAdminPendingReviewsSliceResponse of(List<Review> reviews, Pageable pageable){
		return GetAdminPendingReviewsSliceResponse.builder()
			.lastReviewId(SliceUtil.hasNext(reviews, pageable) ?
				SliceUtil.getLastContent(reviews).getId() : null)
			.reviews(SliceUtil.valueOf(GetAdminsPendingReviews.from(reviews), pageable))
			.build(); 
	}
}
