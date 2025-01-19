package com.clubber.ClubberServer.domain.user.dto;

import com.clubber.ClubberServer.domain.user.domain.User;
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

    public static GetUserReviewsResponse of(User user, List<UserReviewResponse> userReviewRespons){
        return GetUserReviewsResponse.builder()
                .userId(user.getId())
                .userReviews(userReviewRespons)
                .build();
    }
}
