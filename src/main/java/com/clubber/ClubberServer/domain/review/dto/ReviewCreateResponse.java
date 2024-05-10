package com.clubber.ClubberServer.domain.review.dto;


import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.user.domain.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ReviewCreateResponse {

    private final Long userId;
    private final Long clubId;
    private final List<Keyword> keywords;

    public static ReviewCreateResponse of(Review review, List<ReviewKeyword> reviewkeywords){
        List<Keyword> keywords = reviewkeywords.stream()
                .map(ReviewKeyword::getKeyword)
                .collect(Collectors.toList());

        return ReviewCreateResponse.builder()
                .userId(review.getUser().getId())
                .clubId(review.getClub().getId())
                .keywords(keywords)
                .build();
    }
}
