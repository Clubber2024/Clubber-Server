package com.clubber.ClubberServer.domain.review.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeyword;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.global.validator.enums.Enum;
import com.clubber.ClubberServer.global.validator.unique.Unique;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewClubWithContentRequest {

    @Size(max = 100, message = "리뷰 작성은 100자까지 가능합니다")
    @Schema(description = "작성하고자하는 한줄평", example = "활동이 재밌어요")
    private String content;

    @Size(min = 1, message = "1개 이상의 키워드를 선택해주세요")
    @Schema(description = "선택하려는 키워드")
    @Unique
    private List<@Enum(
                target = Keyword.class,
                message = "유효하지 않은 리뷰 키워드입니다"
        ) Keyword> keywords = new ArrayList<>();

    public void toReviewKeywordEntities(Review review){
        keywords.stream()
            .map(ReviewKeyword::from)
            .forEach(reviewKeyword -> reviewKeyword.setReview(review));
    }

    public Review toReviewEntity(User user, Club club){
        return Review.of(user, club, content);
    }
    
}
