package com.clubber.ClubberServer.domain.admin.dto;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.global.common.slice.SliceResponse;
import com.clubber.ClubberServer.global.util.SliceUtil;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetAdminPendingReviewsWithSliceResponse {

	private final Long lastReviewId; 

	private final SliceResponse<GetAdminsReviewByStatusResponse> reviews;

	public static GetAdminPendingReviewsWithSliceResponse of(List<Review> reviews, Pageable pageable){
		return GetAdminPendingReviewsWithSliceResponse.builder()
			.lastReviewId(SliceUtil.hasNext(reviews, pageable) ?
				SliceUtil.getLastContent(reviews).getId() : null)
			.reviews(SliceUtil.valueOf(GetAdminsReviewByStatusResponse.from(reviews), pageable))
			.build(); 
	}
}
