package com.clubber.domain.user.dto;

import com.clubber.domain.domains.user.domain.User;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetUserReviewsResponse {
    private final Long userId;

    private final List<UserReviewResponse> userReviews;

    public static GetUserReviewsResponse of(User user, List<UserReviewResponse> userReviewResponse){
        return GetUserReviewsResponse.builder()
                .userId(user.getId())
                .userReviews(userReviewResponse)
                .build();
    }
}
